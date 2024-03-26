package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import br.com.eventhorizon.messaging.provider.kafka.KafkaMessagingProvider;
import br.com.eventhorizon.messaging.provider.kafka.subscription.KafkaSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.Subscriber;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberFactory;
import br.com.eventhorizon.messaging.provider.subscription.Subscription;
import br.com.eventhorizon.messaging.provider.subscription.UnsupportedSubscriptionException;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class KafkaSubscriberFactory implements SubscriberFactory {

    private final ExecutorService executorService;

    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public String getProviderName() {
        return KafkaMessagingProvider.PROVIDER_NAME;
    }

    @Override
    public <T> Subscriber<T> create(Subscription<T> subscription) {
        if (subscription instanceof KafkaSubscription<T> kafkaSubscription) {
            var name = KafkaMessagingProvider.PROVIDER_NAME + "-subscriber-" + count.getAndIncrement();
            var poller = new KafkaMessagePoller<T>(kafkaSubscription.getSource(), kafkaSubscription.getConfigs());
            return new Subscriber<>(name, executorService, subscription, poller, poller);
        }

        throw new UnsupportedSubscriptionException(subscription, this);
    }
}
