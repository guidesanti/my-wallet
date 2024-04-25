package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilterChain;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Builder
@RequiredArgsConstructor
public class MetricsMessageFilter<T> implements MessageFilter<T> {

    @Override
    public int order() {
        return SubscriberPhase.METRICS.order();
    }

    @Override
    public void filter(List<SubscriberMessage<T>> messages, MessageFilterChain<T> chain) throws Exception {
        var ini = 0L;
        try {
            log.debug("##### MetricsMessageFilter MESSAGE FILTER START #####");
            ini = System.currentTimeMillis();
            chain.next(messages);
        } finally {
            var end = System.currentTimeMillis();
            log.info("Message processing time (ms): {}", end - ini);
            log.debug("##### MetricsMessageFilter MESSAGE FILTER END #####");
        }
    }
}
