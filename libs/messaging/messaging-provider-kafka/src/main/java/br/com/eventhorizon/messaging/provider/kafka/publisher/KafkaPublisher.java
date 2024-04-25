package br.com.eventhorizon.messaging.provider.kafka.publisher;

import br.com.eventhorizon.common.concurrent.ExecutorServiceProvider;
import br.com.eventhorizon.messaging.provider.publisher.Publisher;
import br.com.eventhorizon.messaging.provider.publisher.PublisherException;
import br.com.eventhorizon.messaging.provider.publisher.PublishingRequest;
import br.com.eventhorizon.messaging.provider.publisher.PublishingResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
public class KafkaPublisher implements Publisher {

    private final Producer<Object, Object> producer;

    private final ExecutorService executorService = ExecutorServiceProvider.getDefaultExecutorServiceProvider().getIoExecutorService();

    public KafkaPublisher(Map<String, Object> configs) {
        this.producer = new KafkaProducer<>(configs);
    }

    public KafkaPublisher(Properties properties) {
        this.producer = new KafkaProducer<>(properties);
    }

    @Override
    public <T> Future<PublishingResult> publishAsync(PublishingRequest<T> request) {
        List<Header> headers = buildHeaders(request);
        var record = new ProducerRecord<Object, Object>(request.getDestination(), null, null, null, request.getMessage().content(), headers);
        return executorService.submit(() -> {
            try {
                var future = producer.send(record);
                var result = future.get();
                log.info("Message sent to Kafka: {}", result);
                return PublishingResult.builder().ok(true).build();
            } catch (Exception ex) {
                log.error("Failed to send message to Kafka", ex);
                throw new PublisherException("Failed to send message to Kafka", ex);
            }
        });
    }

    private <T> List<Header> buildHeaders(PublishingRequest<T> request) {
        List<Header> kafkaHeaders = new ArrayList<>();
        var messageHeaders = request.getMessage().headers();
        messageHeaders.names().forEach((headerName) -> {
            messageHeaders.values(headerName).forEach((headerValue) -> {
                kafkaHeaders.add(new RecordHeader(headerName, headerValue.getBytes()));
            });
        });
        return kafkaHeaders;
    }

    @Override
    public void beginTransaction() {
        producer.beginTransaction();
        log.debug("Kafka transaction started");
    }

    @Override
    public void commitTransaction() {
        producer.commitTransaction();
        log.debug("Kafka transaction committed");
    }

    @Override
    public <T> T executeInTransaction(Callable<T> task) throws Exception {
        producer.beginTransaction();
        log.debug("Kafka transaction started");
        var output = task.call();
        producer.commitTransaction();
        log.debug("Kafka transaction committed");
        return output;
    }
}
