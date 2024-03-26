package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.error.ErrorCode;
import br.com.eventhorizon.common.messaging.Message;
import br.com.eventhorizon.common.utils.ExceptionUtils;
import br.com.eventhorizon.messaging.provider.publisher.Publisher;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageChain;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static br.com.eventhorizon.messaging.provider.MessagingProvider.*;

@Slf4j
@Builder
@RequiredArgsConstructor
public class OnErrorPublishToDestinationMessageFilter<T> implements MessageFilter<T> {

    private final Publisher publisher;

    private final String destination;

    @Override
    public int order() {
        return SubscriberPhase.DLQ.order();
    }

    @Override
    public void filter(List<SubscriberMessage<T>> messages, MessageChain<T> chain) throws Exception {
        try {
            log.info("##### OnErrorPublishToDestinationMessageFilter MESSAGE FILTER START #####");
            chain.next(messages);
        } catch (Exception ex) {
            for (var message : messages) {
                publishToDestination(message, ex);
            }
        } finally {
            log.info("##### OnErrorPublishToDestinationMessageFilter MESSAGE FILTER END #####");
        }
    }

    private void publishToDestination(SubscriberMessage<T> message, Exception exception) {
        try {
            publisher.publishAsync(destination, Message.<T>builder()
                    .copy(message)
                    .header(HEADER_ERROR_CATEGORY, ErrorCategory.SERVER_ERROR.getValue())
                    .header(HEADER_ERROR_CODE, ErrorCode.lib("MESSAGING_PROVIDER", "UNEXPECTED_ERROR").toString())
                    .header(HEADER_ERROR_MESSAGE, "Failed to process message due to unexpected exception")
                    .header(HEADER_ERROR_STACK_TRACE, ExceptionUtils.getStackTraceAsString(exception))
                    .build());
        } catch (Exception ex) {
            log.error("Failed to publish to destination", ex);
        }
    }
}
