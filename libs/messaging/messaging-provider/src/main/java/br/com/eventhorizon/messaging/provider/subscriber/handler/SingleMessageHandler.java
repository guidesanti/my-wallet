package br.com.eventhorizon.messaging.provider.subscriber.handler;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;

public interface SingleMessageHandler<T> extends MessageHandler<T> {

    void handle(SubscriberMessage<T> message) throws Exception;
}
