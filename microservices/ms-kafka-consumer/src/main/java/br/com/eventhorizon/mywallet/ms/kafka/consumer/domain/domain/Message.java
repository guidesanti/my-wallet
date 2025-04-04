package br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record Message(UUID batchId, Instant createdAt, long sequence, byte[] content) {
}
