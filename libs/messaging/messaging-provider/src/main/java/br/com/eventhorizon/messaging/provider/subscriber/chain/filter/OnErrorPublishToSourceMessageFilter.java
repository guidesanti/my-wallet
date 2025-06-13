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
public class OnErrorPublishToSourceMessageFilter<T> implements MessageFilter<T> {

    private final Config config;

    private final Publisher publisher;

    private final int maxRetries;

    @Override
    public int order() {
        return SubscriberPhase.RETRY.order();
    }

    @Override
    public void filter(List<SubscriberMessage<T>> messages, MessageFilterChain<T> chain) throws Exception {
        try {
            log.debug("##### OnErrorPublishToSourceMessageFilter MESSAGE FILTER START #####");
            chain.next(messages);
        } catch (Exception ex) {
            for (var message : messages) {
                var nextRetryCount = getNextRetryCount(message);
                if (shouldRetry(nextRetryCount)) {
                    log.error("Message processing failed, publishing to source {} duo to", message.source(), ex);
                    publishToDestination(message, ex, nextRetryCount);
                } else {
                    throw ex;
                }
            }
        } finally {
            log.debug("##### OnErrorPublishToSourceMessageFilter MESSAGE FILTER END #####");
        }
    }

    private int getNextRetryCount(SubscriberMessage<T> message) {
        try {
            return Integer.parseInt(message.headers().firstValue(Header.RETRY_COUNT.getName()).orElse("0")) + 1;
        } catch (Exception ex) {
            return 1;
        }
    }

    private boolean shouldRetry(int nextRetryCount) {
        return nextRetryCount <= maxRetries;
    }

    private void publishToDestination(SubscriberMessage<T> message, Exception exception, int nextRetryCount) {
        try {
            publisher.publishAsync(message.source(), Message.<T>builder()
                    .headers(HeaderUtils.extractCustomHeaders(message.headers()))
                    .headers(HeaderUtils.buildBasePlatformHeaders(config, message.headers()))
                    .header(Header.RETRY_COUNT.getName(), String.valueOf(nextRetryCount))
                    .header(Header.ERROR_MESSAGE.getName(), "Failed to process message due to unexpected exception")
                    .header(Header.ERROR_STACK_TRACE.getName(), ExceptionUtils.getStackTraceAsString(exception))
                    .content(message.content())
                    .build());
        } catch (Exception ex) {
            var errorMessage = String.format("Failed to publish message back to source '%s' with retry count '%s'", message.source(), maxRetries);
            log.error(errorMessage, ex);
            throw new FailureException(errorMessage, ex);
        }
    }
}
