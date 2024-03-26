package br.com.eventhorizon.messaging.provider.subscriber.processor;

import java.util.List;

public interface SubscriberMessagePoller<T> {

    List<SubscriberPolledMessageBatch<T>> poll();
}
