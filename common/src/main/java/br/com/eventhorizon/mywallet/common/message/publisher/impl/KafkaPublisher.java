package br.com.eventhorizon.mywallet.common.message.publisher.impl;

import br.com.eventhorizon.mywallet.common.message.publisher.PublisherRequest;
import br.com.eventhorizon.mywallet.common.message.publisher.PublisherResponse;
import br.com.eventhorizon.mywallet.common.message.publisher.TransactionablePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaPublisher implements TransactionablePublisher {

    private final KafkaTemplate<byte[], Object> kafkaTemplate;

    @Override
    public <T> Future<PublisherResponse> publishAsync(PublisherRequest<T> request) {
        List<Header> headers = buildHeaders(request);
        var record = new ProducerRecord<byte[], Object>(request.getDestination(), null, null, null, request.getMessage().content(), headers);
        try (var executor = Executors.newSingleThreadExecutor()) {
            return executor.submit(() -> {
                try {
                    kafkaTemplate.send(record).get();
                    return PublisherResponse.builder().isOk(true).build();
                } catch (InterruptedException | ExecutionException e) {
                    return PublisherResponse.builder().isOk(false).build();
                }
            });
        }
    }

    private <T> List<Header> buildHeaders(PublisherRequest<T> request) {
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
    @Transactional(rollbackFor = { Exception.class })
    public <T> T transact(Callable<T> task) throws Exception {
        log.info("Saga repository transaction start");
        var output = task.call();
        log.info("Saga repository transaction commit");
        return output;
    }

//    @Override
//    public <T> T transact(Callable<T> task) throws Exception {
//        log.info("Saga publisher transaction start");
//        var output = kafkaTemplate.executeInTransaction(kafkaOperations -> {
//            try {
//                return task.call();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//        log.info("Saga publisher transaction commit");
//        return output;
//    }
}
