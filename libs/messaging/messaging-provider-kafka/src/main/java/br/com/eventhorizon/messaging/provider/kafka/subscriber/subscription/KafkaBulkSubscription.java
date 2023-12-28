package br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.subscription.BulkSubscription;

public interface KafkaBulkSubscription<T> extends KafkaSubscription<T>, BulkSubscription<T> {
}
