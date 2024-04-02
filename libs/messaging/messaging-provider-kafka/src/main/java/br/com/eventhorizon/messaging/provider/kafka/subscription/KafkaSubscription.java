package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.kafka.KafkaMessagingProvider;
import br.com.eventhorizon.messaging.provider.subscription.Subscription;

import java.util.Map;

public interface KafkaSubscription<T> extends Subscription<T> {

    default String getProviderName() {
        return KafkaMessagingProvider.PROVIDER_NAME;
    }

    Map<String, Object> getConfigs();
}
