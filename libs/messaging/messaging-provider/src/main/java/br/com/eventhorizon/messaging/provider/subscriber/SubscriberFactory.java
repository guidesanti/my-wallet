package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;

public interface SubscriberFactory {

    String getProviderName();

    <T> Subscriber<T> create(Subscription<T> subscription);
}
