package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;

public interface Subscriber<T> {

    String getName();

    State getState();

    Subscription<T> getSubscription();

    boolean start();

    boolean stop();

    enum State {
        CREATED,
        STARTING,
        RUNNING,
        STOPPING,
        STOPPED
    }
}
