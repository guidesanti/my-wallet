package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.kafka.KafkaMessagingProvider;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import br.com.eventhorizon.messaging.provider.subscription.BaseSubscription;
import br.com.eventhorizon.messaging.provider.subscription.BulkSubscription;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
@ToString
public class KafkaBulkSubscription<T> extends BaseSubscription<T> implements BulkSubscription<T>, KafkaSubscription<T> {

    private final String providerName = KafkaMessagingProvider.PROVIDER_NAME;

    private final BulkMessageHandler<T> handler;

    private final Map<String, Object> configs;
}
