package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageChain;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LoggerMessageFilter<T> implements MessageFilter<T> {

    @Override
    public int order() {
        return SubscriberPhase.START.before();
    }

    @Override
    public void filter(List<SubscriberMessage<T>> messages, MessageChain<T> chain) throws Exception {
        try {
            log.info("##### LOGGER MESSAGE FILTER START #####");
            chain.next(messages);
        } finally {
            log.info("##### LOGGER MESSAGE FILTER END #####");
        }
    }
}
