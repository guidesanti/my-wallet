package br.com.eventhorizon.messaging.provider.subscriber.chain;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import br.com.eventhorizon.messaging.provider.subscriber.handler.MessageHandler;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MessageChain<T> {

    @NonNull
    private final Iterator<MessageFilter<T>> filters;

    @NonNull
    private final MessageHandler<T> handler;

    public void next(List<SubscriberMessage<T>> messages) throws Exception {
        if (messages == null || messages.isEmpty()) {
            return;
        }
        if (filters.hasNext()) {
            filters.next().filter(messages, this);
        } else if (handler instanceof BulkMessageHandler<T> bulkMessageHandler) {
            log.info("Message handling in BULK mode");
            bulkMessageHandler.handle(messages);
        } else {
            log.info("Message handling in SINGLE mode");
            for (var message : messages) {
                ((SingleMessageHandler<T>) handler).handle(message);
            }
        }
    }
}
