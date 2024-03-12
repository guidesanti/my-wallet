package br.com.eventhorizon.messaging.provider.subscriber.chain;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;

import java.util.List;

public interface MessageFilter<T> {

    int order();

    void filter(List<SubscriberMessage<T>> messages, MessageChain<T> chain) throws Exception;
}
