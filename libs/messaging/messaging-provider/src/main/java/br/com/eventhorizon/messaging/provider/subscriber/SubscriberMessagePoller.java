package br.com.eventhorizon.messaging.provider.subscriber;

import java.util.List;

public interface SubscriberMessagePoller<T> {

    List<SubscriberMessage<T>> poll();
}
