package br.com.eventhorizon.mywallet.ms.kafka.consumer.application.usecase;

import br.com.eventhorizon.common.usecase.UseCase;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain.BatchMarker;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain.BatchStats;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain.Measure;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.input.ConsumeMessageInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class ConsumeMessage implements UseCase<ConsumeMessageInput, Void> {

    private static final long SCRAPE_TIME_NS = 1_000_000_000; // 1 second

    private BatchStats batchStats;

    private long measureStartTime;

    private long measureMessages;

    private long measureBytes;

    private double measureLatency;

    @Override
    public Future<Void> call(ConsumeMessageInput input) {
        if (input.batchMarker() == BatchMarker.START) {
            handleBatchStart(input);
        } else if (input.batchMarker() == BatchMarker.END) {
            handleBatchEnd();
        } else {
            handleMessage(input);
        }
        return null;
    }

    private void handleBatchStart(ConsumeMessageInput input) {
        log.info("Batch started");
        batchStats = new BatchStats(input.batchId(), null, SCRAPE_TIME_NS);
        batchStats.start();
        measureStartTime = System.nanoTime();
        measureMessages = 0L;
        measureBytes = 0L;
    }

    private void handleBatchEnd() {
        log.info("Batch ended");
        batchStats.stop();

        if (measureMessages > 0) {
            var measure = new Measure((System.nanoTime() - measureStartTime) / 1_000_000, measureMessages, measureBytes, measureLatency / measureMessages);
            batchStats.addMeasure(measure);
        }

        logBatchStats(batchStats);
    }

    private void handleMessage(ConsumeMessageInput input) {
        measureMessages++;
        measureBytes += input.message().getContent().length;
        measureLatency += input.latency();

        var now = System.nanoTime();
        var measureDurationNs = (now - measureStartTime);
        if (measureDurationNs >= SCRAPE_TIME_NS) {
            var measure = new Measure(measureDurationNs / 1_000_000, measureMessages, measureBytes, measureLatency / measureMessages);
            batchStats.addMeasure(measure);
            measureStartTime = now;
            measureMessages = 0L;
            measureBytes = 0L;
            measureLatency = 0L;
        }
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
        System.out.println("Measures count: " + stats.getMeasures().size());
        System.out.println("Avg Latency (ms): " + stats.getAvgLatency());
        System.out.println("Min Latency (ms): " + stats.getMinLatency());
        System.out.println("Max Latency (ms): " + stats.getMaxLatency());
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
}
