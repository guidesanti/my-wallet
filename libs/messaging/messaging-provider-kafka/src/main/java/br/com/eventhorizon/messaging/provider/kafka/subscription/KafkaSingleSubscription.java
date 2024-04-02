package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import br.com.eventhorizon.messaging.provider.subscription.BaseSubscription;
import br.com.eventhorizon.messaging.provider.subscription.SingleSubscription;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
@ToString
public class KafkaSingleSubscription<T> extends BaseSubscription<T> implements SingleSubscription<T>, KafkaSubscription<T> {

    private final SingleMessageHandler<T> handler;

    private final Map<String, Object> configs;
}
