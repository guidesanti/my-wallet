package br.com.eventhorizon.messaging.provider.subscriber.processor;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;

import java.util.List;
import java.util.Optional;

public interface SubscriberPolledMessageBatch<T> {

    /**
     * Get the next message.
     *
     * @return the next message
     */
    Optional<SubscriberMessage<T>> next();

    /**
     * Get the next messages.
     * If there are no more messages available an empty list will be returned.
     * If there are count or more messages available, count messages  are returned, otherwise all remaining messages
     * are returned.
     *
     * @param count the number of messages to get
     * @return an empty list if there are no more messages
     */
    List<SubscriberMessage<T>> next(int count);
}
