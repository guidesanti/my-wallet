package br.com.eventhorizon.mywallet.ms.kafka.producer.application.usecase;

import br.com.eventhorizon.common.usecase.UseCase;
import br.com.eventhorizon.mywallet.ms.kafka.producer.application.messaging.MessageProducer;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.BatchStats;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Message;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.ProductionTask;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.input.ProduceMessagesInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.random.RandomGenerator;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProduceMessages implements UseCase<ProduceMessagesInput, Void> {

    private final ExecutorService executorService;

    private final MessageProducer messageProducer;

    @Override
    public Future<Void> call(ProduceMessagesInput input) {
        return executorService.submit(() -> {
            var productionTask = ProductionTask.create("some-batchName", input.topic(), input.numberOfMessages(), input.messageSize());
            var sequence = 0;
            log.info("Starting production task: {}", productionTask);
            productionTask.start();
            while (sequence < input.numberOfMessages()) {
                var message = createMessage(productionTask.getId(), input.messageSize(), sequence);
//                log.info("Producing message {}", message);
                messageProducer.produce(productionTask.getTopic(), message);
                sequence++;
            }
            productionTask.complete();
            log.info("Completed production task: {}", productionTask);
//            logProductionStats(productionTask);
            return null;
        });
    }

    private Message createMessage(UUID batchId, int size, int sequence) {
        var body = new byte[size];
        RandomGenerator.getDefault().nextBytes(body);
        return new Message(batchId, Instant.now(), sequence, body);
    }

    private void logProductionStats(BatchStats stats) {
        log.info("Batch stats:");
        System.out.println("Batch ID: " + stats.getId());
        System.out.println("Batch batchName: " + stats.getName());
        System.out.println("Scrape time (ms): " + 1000 * stats.getScrapeTimeNs());
        System.out.println("Started at: " + stats.getStartInstant());
        System.out.println("Stopped at: " + stats.getStopInstant());
        System.out.println("Duration: " + stats.getDuration());
        System.out.println("Total messages: " + stats.getTotalMessages());
        System.out.println("Total bytes: " + stats.getTotalBytes());
        System.out.println("Measures: " + stats.getMeasures().size());
        System.out.println("Throughput (messages/second): " + stats.getMessagesThroughput());
        System.out.println("Throughput (bytes/second): " + stats.getBytesThroughput());
        System.out.println("Avg Throughput (messages/second): " + stats.getAvgMessagesThroughput());
        System.out.println("Avg Throughput (bytes/second): " + stats.getAvgBytesThroughput());
    }
}
