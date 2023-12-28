package br.com.eventhorizon.messaging.provider.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberFactory;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;

public class UnsupportedSubscriptionException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Subscription of type '%s' is not supported by factory class '%s'";

    public UnsupportedSubscriptionException(Subscription<?> subscription, SubscriberFactory factory) {
        super(String.format(MESSAGE_TEMPLATE, subscription.getClass(), factory.getClass()));
    }
}
