package br.com.eventhorizon.messaging.provider.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;

public interface BulkSubscription<T> extends Subscription<T> {

    BulkMessageHandler<T> getHandler();
}
