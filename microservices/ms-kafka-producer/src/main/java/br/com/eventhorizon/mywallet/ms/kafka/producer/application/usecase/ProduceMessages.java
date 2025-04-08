package br.com.eventhorizon.mywallet.ms.kafka.producer.application.usecase;

import br.com.eventhorizon.common.usecase.UseCase;
import br.com.eventhorizon.mywallet.ms.kafka.producer.application.messaging.MessageProducer;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Measure;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Message;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Batch;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.BatchStats;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.input.BatchParamsInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.random.RandomGenerator;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProduceMessages implements UseCase<BatchParamsInput, Void> {

    private final ExecutorService executorService;

    private final MessageProducer messageProducer;

    @Override
    public Future<Void> call(BatchParamsInput input) {
        return executorService.submit(() -> {
            var productionTask = Batch.create(input.batchName(), input.topic(), input.period(), input.messageSize());
            var scrapeTimeNs = 1_000_000_000; // Scrape time in nanoseconds
            var startTime = System.currentTimeMillis();
            var sequence = 0;
            var measureStartTime = System.nanoTime();
            var measureMessages = 0L;
            var measureBytes = 0L;
            var stats = new BatchStats(productionTask.getId(), input.batchName(), scrapeTimeNs);
            log.info("Starting production task: {}", productionTask);
            productionTask.start();
            stats.start();
            messageProducer.produceBatchMarkerMessage(productionTask.getTopic(), createMessage(productionTask.getId(), 0, -1), MessageProducer.BatchMarker.START);
            while ((System.currentTimeMillis() - startTime) < input.period()) {
                var message = createMessage(productionTask.getId(), input.messageSize(), sequence);
                messageProducer.produce(productionTask.getTopic(), message);
                sequence++;

                measureMessages++;
                measureBytes += input.messageSize();

                var now = System.nanoTime();
                var measureDurationNs = (now - measureStartTime);
                if (measureDurationNs >= scrapeTimeNs) {
                    var measure = new Measure(measureDurationNs / 1_000_000, measureMessages, measureBytes);
                    stats.addMeasure(measure);
                    measureStartTime = now;
                    measureMessages = 0L;
                    measureBytes = 0L;
                }
            }

            // Register last measure
            if (measureMessages > 0) {
                var measure = new Measure((System.nanoTime() - measureStartTime) / 1_000_000, measureMessages, measureBytes);
                stats.addMeasure(measure);
            }

            messageProducer.produceBatchMarkerMessage(productionTask.getTopic(), createMessage(productionTask.getId(), 0, -1), MessageProducer.BatchMarker.END);
            stats.stop();
            productionTask.complete();
            log.info("Completed production task: {}", productionTask);
            logBatchStats(stats);
            saveResults(stats);

            return null;
        });
    }

    private Message createMessage(UUID batchId, int size, int sequence) {
        var body = new byte[size];
        RandomGenerator.getDefault().nextBytes(body);
        return new Message(batchId, Instant.now(), sequence, body);
    }

    private void logBatchStats(BatchStats stats) {
        System.out.println();
        System.out.println("====================================== Batch Stats ======================================");
        System.out.println("Batch ID: " + stats.getId());
        System.out.println("Batch batchName: " + stats.getName());
        System.out.println("Scrape time (ms): " + stats.getScrapeTimeNs() / 1_000_000);
        System.out.println("Started at: " + stats.getStartInstant());
        System.out.println("Stopped at: " + stats.getStopInstant());
        System.out.println("Duration: " + stats.getDuration());
        System.out.println("Total messages: " + stats.getTotalMessages());
        System.out.println("Total bytes: " + stats.getTotalBytes());
        System.out.println("Measures: " + stats.getMeasures().size());
        System.out.println("Avg Throughput (messages/second): " + stats.getAvgMessagesThroughput());
        System.out.println("Avg Throughput (bytes/second): " + stats.getAvgBytesThroughput());
        System.out.println("Min Throughput (messages/second): " + stats.getMinMessagesThroughput());
        System.out.println("Min Throughput (bytes/second): " + stats.getMinBytesThroughput());
        System.out.println("Max Throughput (messages/second): " + stats.getMaxMessagesThroughput());
        System.out.println("Max Throughput (bytes/second): " + stats.getMaxBytesThroughput());
        System.out.println("Measures: " + stats.getMeasures());
        System.out.println("=========================================================================================");
        System.out.println();
    }

    private void saveResults(BatchStats stats) throws IOException {
        var resultsDirPath = Path.of("/results");
        if (!Files.exists(resultsDirPath)) {
            Files.createFile(resultsDirPath);
        }

        // Save batch measures
        var resultFilePath = resultsDirPath.resolve(stats.getId() + "-producer" + ".csv");
        var csvOutputFile = new File(resultFilePath.toUri());
        try (var pw = new PrintWriter(csvOutputFile)) {
            pw.println("durationMs,messages,bytes,messagesThroughput,bytesThroughput");
            stats.getMeasures().forEach(measure -> {
                pw.println(measure.durationMs() + ","
                        + measure.messages() + ","
                        + measure.bytes() + ","
                        + measure.getMessagesThroughput() + ","
                        + measure.getBytesThroughput());
            });
        }

        // Save batch overall results
        var producerResultsFilePath = resultsDirPath.resolve("producer-results" + ".csv");
        if (!Files.exists(producerResultsFilePath)) {
            Files.createFile(producerResultsFilePath);
            try (var fw = new FileWriter(new File(producerResultsFilePath.toUri()), true)) {
                fw.append("batchId,batchName,startedAt,stoppedAt,durationMs,totalMessages,totalBytes,measures," +
                        "producerAvgMessagesThroughput,producerAvgBytesThroughput," +
                        "producerMinMessagesThroughput,producerMinBytesThroughput," +
                        "producerMaxMessagesThroughput,producerMaxBytesThroughput");
            }
        }
        var producerResultsFile = new File(producerResultsFilePath.toUri());
        try (var fw = new FileWriter(producerResultsFile, true)) {
            System.out.println(producerResultsFile.getAbsolutePath());
            var string = "\n" + stats.getId() + ","
                    + stats.getName() + ","
                    + stats.getStartInstant() + ","
                    + stats.getStopInstant() + ","
                    + stats.getDuration() + ","
                    + stats.getTotalMessages() + ","
                    + stats.getTotalBytes() + ","
                    + stats.getMeasures().size() + ","
                    + stats.getAvgMessagesThroughput() + ","
                    + stats.getAvgBytesThroughput() + ","
                    + stats.getMinMessagesThroughput() + ","
                    + stats.getMinBytesThroughput() + ","
                    + stats.getMaxMessagesThroughput() + ","
                    + stats.getMaxBytesThroughput();
            fw.write(string);
        } catch (IOException ex) {
            log.error("Failed to write results to '/producer-results.csv'", ex);
        }

        log.info("Saved results to {}", resultFilePath);
    }
}
