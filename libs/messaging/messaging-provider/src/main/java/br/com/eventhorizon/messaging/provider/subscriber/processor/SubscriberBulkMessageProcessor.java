package br.com.eventhorizon.messaging.provider.subscriber.processor;

import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageChainFactory;
import br.com.eventhorizon.messaging.provider.subscription.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SubscriberBulkMessageProcessor<T> implements SubscriberMessageProcessor<T> {

    private final Subscription<T> subscription;

    private final SubscriberPolledMessageBatch<T> polledMessages;

    private final SubscriberMessageProcessorListener<T> listener;

    @Override
    public void run() {
        listener.onProcessStarted(polledMessages);
        var messages = polledMessages.next(Integer.MAX_VALUE);
        try {
            var chain = MessageChainFactory.create(subscription);
            listener.onMessageHandlingStarted(polledMessages, messages);
            chain.next(messages);
            listener.onMessageHandlingSucceeded(polledMessages, messages);
        } catch (Exception ex) {
            log.error("Failed to process messages", ex);
            listener.onMessageHandlingFailed(polledMessages, messages);
        }
        listener.onProcessFinished(polledMessages);
    }
}
