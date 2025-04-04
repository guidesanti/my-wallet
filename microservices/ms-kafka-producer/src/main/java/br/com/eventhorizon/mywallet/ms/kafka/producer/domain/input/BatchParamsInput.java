package br.com.eventhorizon.mywallet.ms.kafka.producer.domain.input;

public record BatchParamsInput(String batchName, String topic, int period, int messageSize) {
}
