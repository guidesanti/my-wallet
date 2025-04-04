package br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.*;

@ToString
public class ProductionTask {

    @Getter
    private final UUID id;

    @Getter
    private final String name;

    @Getter
    private final String topic;

    @Getter
    private final long numberOfMessages;

    @Getter
    private final long messageSize;

    @Getter
    private Status status;

    private final List<Status> statusHistory = new ArrayList<>();

    private ProductionTask(UUID id, String name, String topic, int numberOfMessages, int messageSize) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.numberOfMessages = numberOfMessages;
        this.messageSize = messageSize;
        setStatus(StatusCode.CREATED, Instant.now());
    }

    public List<Status> getStatusHistory() {
        return Collections.unmodifiableList(this.statusHistory);
    }

    public static ProductionTask create(String name, String topic, int numberOfMessages, int messageSize) {
        return new ProductionTask(UUID.randomUUID(), name, topic, numberOfMessages, messageSize);
    }

    public static ProductionTask create(UUID id, String name, String topic, int numberOfMessages, int messageSize) {
        return new ProductionTask(id, name, topic, numberOfMessages, messageSize);
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
