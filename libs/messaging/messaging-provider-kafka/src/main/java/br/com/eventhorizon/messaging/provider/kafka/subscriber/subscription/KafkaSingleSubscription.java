package br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.subscription.SingleSubscription;

public interface KafkaSingleSubscription<T> extends KafkaSubscription<T>, SingleSubscription<T> {
}
