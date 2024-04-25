package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.kafka.KafkaMessagingProvider;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.DefaultSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.SingleSubscription;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
@ToString
public class KafkaSingleSubscription<T> extends DefaultSubscription<T> implements SingleSubscription<T>, KafkaSubscription<T> {

    private final Map<String, Object> configs;

    public String getProviderName() {
        return KafkaMessagingProvider.PROVIDER_NAME;
    }

    public @NonNull SingleMessageHandler<T> getHandler() {
        return (SingleMessageHandler<T>) super.getHandler();
    }
}
