package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.saga.transaction.SagaTransaction;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class KafkaSagaTransaction<R, M> extends SagaTransaction<R, M> {

    private final Map<String, Object> kafkaConsumerConfig;
}
