package br.com.eventhorizon.messaging.provider.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.handler.MessageHandler;

import java.util.List;

public interface Subscription<T> {

    String getId();

    String getProviderName();

    List<MessageFilter<T>> getFilters();

    MessageHandler<T> getHandler();

    String getSource();

    Class<T> getSourceType();

    SubscriptionProperties getProperties();
}
