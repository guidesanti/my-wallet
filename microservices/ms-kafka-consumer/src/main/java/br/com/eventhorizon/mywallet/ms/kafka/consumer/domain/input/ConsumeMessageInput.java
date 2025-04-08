package br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.input;

import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain.BatchMarker;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain.Message;

import java.util.UUID;

public record ConsumeMessageInput(UUID batchId, BatchMarker batchMarker, Message message, double latency) {
}
