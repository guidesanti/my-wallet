package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessagePoller;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.Closeable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class KafkaMessagePoller<T> implements SubscriberMessagePoller<T>, Closeable {

    private final KafkaConsumer<byte[], T> kafkaConsumer;

    private final String topic;

    private boolean subscribed;

    public KafkaMessagePoller(String topic, Map<String, Object> config) {
        this.kafkaConsumer = new KafkaConsumer<>(config);
        this.topic = topic;
        this.subscribed = false;
    }

    @Override
    public List<SubscriberMessage<T>> poll() {
        try {
            subscribe();

            List<SubscriberMessage<T>> messages = new ArrayList<>();
            ConsumerRecords<byte[], T> records = kafkaConsumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                var messageBuilder = SubscriberMessage.<T>builder();
                record.headers().forEach(header -> messageBuilder.header(header.key(), new String(header.value())));
                messageBuilder.content(record.value());
                messages.add(messageBuilder.build());
            });
            return messages;
        } catch (Exception ex) {
            log.error("Unexpected exception while polling from Kafka", ex);
            return Collections.emptyList();
        }
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
