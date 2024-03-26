package br.com.eventhorizon.messaging.provider.subscriber.processor;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;

import java.util.List;

public interface SubscriberMessageProcessorListener<T> {

    void onProcessStarted(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch);

    void onMessageHandlingStarted(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch, List<SubscriberMessage<T>> subscriberMessages);

    void onMessageHandlingSucceeded(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch, List<SubscriberMessage<T>> subscriberMessages);

    void onMessageHandlingFailed(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch, List<SubscriberMessage<T>> subscriberMessages);

    void onProcessFinished(SubscriberPolledMessageBatch<T> subscriberPolledMessageBatch);
}
