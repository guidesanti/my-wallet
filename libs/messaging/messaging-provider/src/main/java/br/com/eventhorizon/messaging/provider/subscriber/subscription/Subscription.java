package br.com.eventhorizon.messaging.provider.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.handler.MessageHandler;

public interface Subscription<T> {

    String getId();

    String getProviderName();

    MessageHandler<T> getHandler();

    String getSource();

    Class<T> getSourceType();
}
