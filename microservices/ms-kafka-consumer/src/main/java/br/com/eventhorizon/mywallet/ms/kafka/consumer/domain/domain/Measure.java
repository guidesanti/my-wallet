package br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain;

public record Measure(long durationMs, long messages, long bytes, double latency) {

    public double getMessagesThroughput() {
        return (double) 1000 * messages / durationMs;
    }

    public double getBytesThroughput() {
        return (double) 1000 * bytes / durationMs;
    }
}
