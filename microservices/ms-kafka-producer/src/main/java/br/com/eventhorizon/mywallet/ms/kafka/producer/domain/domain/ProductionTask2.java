package br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ToString
public class ProductionTask2 {

    @Getter
    private final UUID id;

    @Getter
    private final String name;

    @Getter
    private final String topic;

    @Getter
    private final long period;

    @Getter
    private final long messageSize;

    @Getter
    private Status status;

    private final List<Status> statusHistory = new ArrayList<>();

    private ProductionTask2(UUID id, String name, String topic, int period, int messageSize) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.period = period;
        this.messageSize = messageSize;
        setStatus(StatusCode.CREATED, Instant.now());
    }

    public List<Status> getStatusHistory() {
        return Collections.unmodifiableList(this.statusHistory);
    }

    public static ProductionTask2 create(String name, String topic, int numberOfMessages, int messageSize) {
        return new ProductionTask2(UUID.randomUUID(), name, topic, numberOfMessages, messageSize);
    }

    public static ProductionTask2 create(UUID id, String name, String topic, int numberOfMessages, int messageSize) {
        return new ProductionTask2(id, name, topic, numberOfMessages, messageSize);
    }

    public void start() {
        if (this.status.status == StatusCode.RUNNING) {
            throw new IllegalStateException("ProductionTask already started");
        }
        if (this.status.status == StatusCode.COMPLETED) {
            throw new IllegalStateException("ProductionTask already completed");
        }
        if (this.status.status == StatusCode.CREATED) {
            setStatus(StatusCode.RUNNING, Instant.now());
        }
    }

    public void complete() {
        if (this.status.status == StatusCode.CREATED) {
            throw new IllegalStateException("ProductionTask not started yet");
        }
        if (this.status.status == StatusCode.COMPLETED) {
            throw new IllegalStateException("ProductionTask already completed");
        }
        if (this.status.status == StatusCode.RUNNING) {
            setStatus(StatusCode.COMPLETED, Instant.now());
        }
    }

    private void setStatus(StatusCode statusCode, Instant timestamp) {
        this.status = new Status(statusCode, timestamp);
        this.statusHistory.add(status);
    }

    public record Status(StatusCode status, Instant instant) {
    }

    public enum StatusCode {
        CREATED,
        RUNNING,
        COMPLETED
    }
}
