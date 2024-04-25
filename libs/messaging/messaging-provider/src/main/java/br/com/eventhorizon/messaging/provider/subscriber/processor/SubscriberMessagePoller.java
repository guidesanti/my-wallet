package br.com.eventhorizon.messaging.provider.subscriber.processor;

import java.io.Closeable;
import java.util.List;

public interface SubscriberMessagePoller<T> extends Closeable {

    void init();

    List<SubscriberPolledMessageBatch<T>> poll();
}
