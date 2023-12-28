package br.com.eventhorizon.messaging.provider.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;

public interface SingleSubscription<T> extends Subscription<T> {

    SingleMessageHandler<T> getHandler();
}
