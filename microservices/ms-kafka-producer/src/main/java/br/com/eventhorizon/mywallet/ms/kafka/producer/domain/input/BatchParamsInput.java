package br.com.eventhorizon.mywallet.ms.kafka.producer.domain.input;

public record BatchParamsInput(String name, String topic, int period, int messageSize) {
}
