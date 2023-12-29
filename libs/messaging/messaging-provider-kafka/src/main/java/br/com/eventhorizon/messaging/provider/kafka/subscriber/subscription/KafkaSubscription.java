package br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;

import java.util.Map;

public interface KafkaSubscription<T> extends Subscription<T> {

    Map<String, Object> getKafkaConsumerConfig();
}
