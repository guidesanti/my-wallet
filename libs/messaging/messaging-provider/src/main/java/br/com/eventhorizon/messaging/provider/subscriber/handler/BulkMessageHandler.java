package br.com.eventhorizon.messaging.provider.subscriber.handler;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;

import java.util.List;

public interface BulkMessageHandler<T> extends MessageHandler<T> {

    void handle(List<SubscriberMessage<T>> messages) throws Exception;
}
