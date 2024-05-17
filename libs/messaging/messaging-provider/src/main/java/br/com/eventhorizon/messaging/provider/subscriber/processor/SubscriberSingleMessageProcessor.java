package br.com.eventhorizon.messaging.provider.subscriber.processor;

import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilterChainFactory;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SubscriberSingleMessageProcessor<T> implements SubscriberMessageProcessor<T> {

    private final Subscription<T> subscription;

    private final SubscriberPolledMessageBatch<T> polledMessages;

    private final SubscriberMessageProcessorListener<T> listener;

    @Override
    public void run() {
        log.debug("Starting processing message batch: {}", polledMessages);
        listener.onProcessStarted(polledMessages);
        for (var next = polledMessages.next(); next.isPresent(); next = polledMessages.next()) {
            var message = next.get();
            log.debug("Starting processing message: {}", message);
            try {
                var chain = MessageFilterChainFactory.create(subscription);
                listener.onMessageHandlingStarted(polledMessages, List.of(message));
                chain.next(List.of(message));
                listener.onMessageHandlingSucceeded(polledMessages, List.of(message));
            } catch (Exception ex) {
                log.error("Failed processing message: {}", message, ex);
                listener.onMessageHandlingFailed(polledMessages, List.of(message));
                break;
            }
        }
        log.debug("Finishing processing message batch: {}", polledMessages);
        listener.onProcessFinished(polledMessages);
    }
}
