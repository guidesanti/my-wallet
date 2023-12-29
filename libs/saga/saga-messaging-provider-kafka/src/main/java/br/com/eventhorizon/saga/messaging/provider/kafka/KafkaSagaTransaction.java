package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.saga.transaction.DefaultSagaTransaction;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class KafkaSagaTransaction<T> extends DefaultSagaTransaction<T> {

    private final Map<String, Object> kafkaConsumerConfig;
}
