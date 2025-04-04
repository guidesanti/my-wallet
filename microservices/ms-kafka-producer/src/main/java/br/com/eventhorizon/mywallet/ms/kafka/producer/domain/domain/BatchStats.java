package br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain;

import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class BatchStats {

    private final UUID id;

    private final String name;

    private final long scrapeTimeNs;

    private Instant startInstant;

    private Instant stopInstant;

    private final List<Measure> measures = new ArrayList<>();

    private double minMessagesThroughput = Double.MAX_VALUE;

    private double minBytesThroughput = Double.MAX_VALUE;

    private double maxMessagesThroughput = Double.MIN_VALUE;

    private double maxBytesThroughput = Double.MIN_VALUE;

    public Duration getDuration() {
        return Objects.nonNull(startInstant) && Objects.nonNull(stopInstant)
                ? Duration.between(startInstant, stopInstant)
                : Duration.ZERO;
    }

    public long getTotalMessages() {
        return measures.stream().map(Measure::messages).reduce(0L, Long::sum);
    }

    public long getTotalBytes() {
        return measures.stream().map(Measure::bytes).reduce(0L, Long::sum);
    }

    public double getMessagesThroughput() {
        return getDuration().equals(Duration.ZERO) ? 0.0 : (double) getTotalMessages() / (getDuration().toMillis() / 1000.0);
    }

    public double getBytesThroughput() {
        return getDuration().equals(Duration.ZERO) ? 0.0 : (double) getTotalBytes() / (getDuration().toMillis() / 1000.0);
    }

    public double getAvgMessagesThroughput() {
        return measures.stream().map(Measure::getMessagesThroughput).reduce(0.0, Double::sum) / measures.size();
    }

    public double getAvgBytesThroughput() {
        return measures.stream().map(Measure::getBytesThroughput).reduce(0.0, Double::sum) / measures.size();
    }

    public void addMeasure(Measure measure) {
        measures.add(measure);
        minMessagesThroughput = Math.min(minMessagesThroughput, measure.getMessagesThroughput());
        minBytesThroughput = Math.min(minBytesThroughput, measure.getBytesThroughput());
        maxMessagesThroughput = Math.max(maxMessagesThroughput, measure.getMessagesThroughput());
        maxBytesThroughput = Math.max(maxBytesThroughput, measure.getBytesThroughput());
    }

    public void start() {
        startInstant = Instant.now();
    }

    public void stop() {
        stopInstant = Instant.now();
    }
}
