package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.common.Common;
import br.com.eventhorizon.messaging.provider.Header;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilterChain;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Builder
@RequiredArgsConstructor
public class LoggerMessageFilter<T> implements MessageFilter<T> {

    private static final String DELIMITER = ",";

    private static final String UNKNOWN = "unknown";

    @Override
    public int order() {
        return SubscriberPhase.START.before();
    }

    @Override
    public void filter(List<SubscriberMessage<T>> messages, MessageFilterChain<T> chain) throws Exception {
        try {
            addMDCKeys(messages);
            log.debug("##### LOGGER MESSAGE FILTER START #####");
            chain.next(messages);
        } catch (Exception ex) {
            log.error("Message processing failed", ex);
        } finally {
            log.debug("##### LOGGER MESSAGE FILTER END #####");
            removeMDCKeys();
        }
    }

    private void addMDCKeys(List<SubscriberMessage<T>> messages) {
        try {
            MDC.put(Common.MDCKey.IDEMPOTENCE_ID, buildCompoundIdempotenceId(messages));
            MDC.put(Common.MDCKey.TRACE_ID, buildCompoundTraceId(messages));
        } catch (Exception ex) {
            log.error("Failed to add MDC keys", ex);
        }
    }

    private void removeMDCKeys() {
        try {
            MDC.remove(Common.MDCKey.IDEMPOTENCE_ID);
            MDC.remove(Common.MDCKey.TRACE_ID);
        } catch (Exception ex) {
            log.error("Failed to remove MDC keys", ex);
        }
    }

    private String buildCompoundIdempotenceId(List<SubscriberMessage<T>> messages) {
        var compoundIdempotenceId = new StringJoiner(DELIMITER);
        messages.stream()
                .map(message -> message.headers().firstValue(Header.IDEMPOTENCE_ID.getName()).orElse(UNKNOWN))
                .forEach(compoundIdempotenceId::add);
        return compoundIdempotenceId.toString();
    }

    private String buildCompoundTraceId(List<SubscriberMessage<T>> messages) {
        var compoundTraceId = new StringJoiner(DELIMITER);
        messages.stream()
                .map(message -> message.headers().firstValue(Header.TRACE_ID.getName()).orElse(UNKNOWN))
                .forEach(compoundTraceId::add);
        return compoundTraceId.toString();
    }
}
