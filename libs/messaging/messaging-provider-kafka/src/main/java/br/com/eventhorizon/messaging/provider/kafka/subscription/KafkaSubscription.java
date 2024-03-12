package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.subscription.Subscription;

import java.util.Map;

public interface KafkaSubscription<T> extends Subscription<T> {

    Map<String, Object> getConfigs();
}
