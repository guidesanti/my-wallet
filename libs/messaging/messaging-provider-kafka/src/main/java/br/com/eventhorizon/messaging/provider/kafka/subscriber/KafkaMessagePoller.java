package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.processor.SubscriberMessagePoller;
import br.com.eventhorizon.messaging.provider.subscriber.processor.SubscriberMessageProcessorListener;
import br.com.eventhorizon.messaging.provider.subscriber.processor.SubscriberPolledMessageBatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.io.Closeable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class KafkaMessagePoller<T> implements SubscriberMessagePoller<T>, SubscriberMessageProcessorListener<T>, Closeable {

    private static final long MIN_POLL_INTERVAL_MS = 100L;

    private static final long MAX_POLL_INTERVAL_MS = 300_000L;

    private static final long COMMIT_INTERVAL_MS = 5_000L;

    private final KafkaConsumer<byte[], T> kafkaConsumer;

    private final ConcurrentMap<TopicPartition, KafkaSubscriberPolledMessageBatch<T>> activeBatches;

    private final String topic;

    private boolean subscribed;

    private long pollInterval;

    private long lastCommitTime;

    public KafkaMessagePoller(String topic, Map<String, Object> config) {
        checkKafkaConfig(config);
        this.kafkaConsumer = new KafkaConsumer<>(config);
        this.activeBatches = new ConcurrentHashMap<>();
        this.topic = topic;
        this.subscribed = false;
        this.pollInterval = MIN_POLL_INTERVAL_MS;
        this.lastCommitTime = System.currentTimeMillis();
    }

    @Override
    public List<SubscriberPolledMessageBatch<T>> poll() {
        try {
            log.debug("Kafka message polling started");
            subscribe();
            commitOffsets();
            handleFinishedBatches();
            return doPoll();
        } catch (Exception ex) {
            log.error("Unexpected exception while polling from Kafka", ex);
            return Collections.emptyList();
        } finally {
            log.debug("Kafka message polling finished");
        }
    }

    private void checkKafkaConfig(Map<String, Object> config) {
        // Disable auto commit, it will be handled manually by this class
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    }

    private List<SubscriberPolledMessageBatch<T>> doPoll() {
        ConsumerRecords<byte[], T> records = kafkaConsumer.poll(Duration.ofMillis(pollInterval));
        List<SubscriberPolledMessageBatch<T>> polledBatches = new ArrayList<>();
        var partitions = records.partitions();
        partitions.forEach(topicPartition -> {
            var list = new LinkedList<KafkaSubscriberMessage<T>>();
            records.records(topicPartition).forEach(record -> {
                var messageBuilder = KafkaSubscriberMessage.<T>builder();
                record.headers().forEach(header -> messageBuilder.header(header.key(), new String(header.value())));
                messageBuilder
                        .source(topic)
                        .offset(record.offset())
                        .content(record.value());
                list.add(messageBuilder.build());
                log.debug("Added offset: {} @ {}", record.offset(), record.partition());
            });
            var batch = new KafkaSubscriberPolledMessageBatch<>(topicPartition, list);
            log.debug("New polled message batch: {}", batch);
            polledBatches.add(batch);
            activeBatches.put(topicPartition, batch);
        });
        kafkaConsumer.pause(partitions);
        log.debug("Paused partitions: {}", partitions);
        handlePollInterval();
        return polledBatches;
    }

    private void handlePollInterval() {
        pollInterval = activeBatches.isEmpty() ? Math.min(2 * pollInterval, MAX_POLL_INTERVAL_MS) : MIN_POLL_INTERVAL_MS;
        log.debug("Polling interval: {}", pollInterval);
    }

    private void subscribe() {
        if (!subscribed) {
            try {
                kafkaConsumer.subscribe(Collections.singletonList(topic));
                log.info("Successfully subscribed to Kafka topics {}", topic);
                subscribed = true;
            } catch (Exception ex) {
                log.error("Failed to subscribe to Kafka topics", ex);
            }
        }
    }

    private void unsubscribe() {
        if (subscribed) {
            try {
                kafkaConsumer.unsubscribe();
                log.info("Successfully unsubscribed to Kafka topics {}", topic);
                subscribed = false;
            } catch (Exception ex) {
                log.error("Failed to unsubscribe to Kafka topics", ex);
            }
        }
    }

    private void commitOffsets() {
        var now = System.currentTimeMillis();
        if (now - lastCommitTime > COMMIT_INTERVAL_MS) {
            var offsetsToCommit = new HashMap<TopicPartition, OffsetAndMetadata>();
            activeBatches.forEach((topicPartition, batch) -> {
                var lastProcessedOffset = batch.getStatus().getLastProcessedOffset();
                if (lastProcessedOffset >= 0 && lastProcessedOffset >= batch.getStatus().getLastCommittedOffset()) {
                    long offsetToCommit = lastProcessedOffset + 1;
                    offsetsToCommit.put(topicPartition, new OffsetAndMetadata(offsetToCommit));
                    batch.getStatus().setLastCommittedOffset(offsetToCommit);
                    log.debug("Offset committed for batch: {}", batch);
                }
            });
            kafkaConsumer.commitSync(offsetsToCommit);
            lastCommitTime = now;
        }
    }

    private void handleFinishedBatches() {
        var finishedPartitions = activeBatches.values().stream()
                .filter(batch -> batch.getStatus().isFinished())
                .map(KafkaSubscriberPolledMessageBatch::getTopicPartition)
                .toList();
        finishedPartitions.forEach(activeBatches::remove);
        kafkaConsumer.resume(finishedPartitions);
        log.debug("Finished processing polled batch messages and resumed partitions: {}", finishedPartitions);
    }

    @Override
    public void onProcessStarted(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch) {
        var batch = (KafkaSubscriberPolledMessageBatch<T>) subscriberPolledMessageBatch;
        batch.getStatus().setProcessingStatus(KafkaSubscriberPolledMessageBatchStatus.ProcessingStatus.PROCESSING);
        log.info("Batch processing started: {}", batch);
    }

    @Override
    public void onMessageHandlingStarted(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch, List<SubscriberMessage<T>> subscriberMessages) {
        log.info("Message handling started for offsets: {}", subscriberMessages.stream()
                .map(subscriberMessage -> ((KafkaSubscriberMessage<T>)subscriberMessage).offset())
                .toList());
    }

    @Override
    public void onMessageHandlingSucceeded(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch, List<SubscriberMessage<T>> subscriberMessages) {
        var batch = (KafkaSubscriberPolledMessageBatch<T>) subscriberPolledMessageBatch;
        var messages = subscriberMessages.stream()
                .map(subscriberMessage -> ((KafkaSubscriberMessage<T>)subscriberMessage))
                .toList();
        batch.getStatus().setLastProcessedOffset(messages.get(messages.size() - 1).offset());

        log.info("Message handling succeeded for offsets: {}", messages.stream()
                .map(KafkaSubscriberMessage::offset)
                .toList());
    }

    @Override
    public void onMessageHandlingFailed(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch, List<SubscriberMessage<T>> subscriberMessages) {
        log.info("Message handling failed for offsets: {}", subscriberMessages.stream()
                .map(subscriberMessage -> ((KafkaSubscriberMessage<T>)subscriberMessage).offset())
                .toList());
    }

    @Override
    public void onProcessFinished(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch) {
        var batch = (KafkaSubscriberPolledMessageBatch<T>) subscriberPolledMessageBatch;
        batch.getStatus().setProcessingStatus(KafkaSubscriberPolledMessageBatchStatus.ProcessingStatus.FINISHED);
        log.info("Batch processing finished: {}", batch);
    }

    @Override
    public void close() {
        unsubscribe();
        try {
            kafkaConsumer.close();
        } catch (Exception ex) {
            log.error("Failed to close Kafka consumer", ex);
        }
    }
}
