package br.com.eventhorizon.mywallet.ms.kafka.producer.domain.input;

public record ProduceMessagesInput(String name, String topic, int numberOfMessages, int messageSize) {
}
