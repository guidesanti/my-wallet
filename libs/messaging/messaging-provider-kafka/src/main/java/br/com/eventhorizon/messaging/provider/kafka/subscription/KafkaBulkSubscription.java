package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.kafka.KafkaMessagingProvider;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.DefaultSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.BulkSubscription;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
@ToString
public class KafkaBulkSubscription<T> extends DefaultSubscription<T> implements BulkSubscription<T>, KafkaSubscription<T> {

    private final Map<String, Object> configs;

    public String getProviderName() {
        return KafkaMessagingProvider.PROVIDER_NAME;
    }

    public @NonNull BulkMessageHandler<T> getHandler() {
        return (BulkMessageHandler<T>) super.getHandler();
    }
}
