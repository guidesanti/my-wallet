package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.exception.FailureException;
import br.com.eventhorizon.common.messaging.Message;
import br.com.eventhorizon.common.utils.ExceptionUtils;
import br.com.eventhorizon.messaging.provider.Header;
import br.com.eventhorizon.messaging.provider.publisher.Publisher;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilterChain;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import br.com.eventhorizon.messaging.provider.utils.HeaderUtils;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Builder
@RequiredArgsConstructor
public class OnErrorPublishToDestinationMessageFilter<T> implements MessageFilter<T> {

    private final Config config;

    private final Publisher publisher;

    private final String destination;

    @Override
    public int order() {
        return SubscriberPhase.DLQ.order();
    }

    @Override
    public void filter(List<SubscriberMessage<T>> messages, MessageFilterChain<T> chain) throws Exception {
        try {
            log.debug("##### OnErrorPublishToDestinationMessageFilter MESSAGE FILTER START #####");
            chain.next(messages);
        } catch (Exception ex) {
            for (var message : messages) {
                publishToDestination(message, ex);
            }
        } finally {
            log.debug("##### OnErrorPublishToDestinationMessageFilter MESSAGE FILTER END #####");
        }
    }

    private void publishToDestination(SubscriberMessage<T> message, Exception exception) {
        try {
            publisher.publishAsync(destination, Message.<T>builder()
                    .headers(HeaderUtils.extractCustomHeaders(message.headers()))
                    .headers(HeaderUtils.buildBasePlatformHeaders(config, message.headers()))
                    .header(Header.RETRY_COUNT.getName(), message.headers().values(Header.RETRY_COUNT.getName()))
                    .header(Header.ERROR_MESSAGE.getName(), "Failed to process message due to unexpected exception")
                    .header(Header.ERROR_STACK_TRACE.getName(), ExceptionUtils.getStackTraceAsString(exception))
                    .content(message.content())
                    .build());
        } catch (Exception ex) {
            var errorMessage = String.format("Failed to publish message destination '%s'", destination);
            log.error(errorMessage, ex);
            throw new FailureException(errorMessage, ex);
        }
    }
}
