package br.com.eventhorizon.mywallet.ms.kafka.producer.domain.input;

public record ProduceMessagesInput(String batchName, String topic, int numberOfMessages, int messageSize) {
}
