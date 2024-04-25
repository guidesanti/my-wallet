package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.exception.BaseErrorException;
import br.com.eventhorizon.messaging.provider.MessagingProviderError;
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
public class LoggerMessageFilter<T> implements MessageFilter<T> {

    @Override
    public int order() {
        return SubscriberPhase.START.before();
    }

    @Override
    public void filter(List<SubscriberMessage<T>> messages, MessageFilterChain<T> chain) throws Exception {
        try {
            log.debug("##### LOGGER MESSAGE FILTER START #####");
            chain.next(messages);
        } catch (BaseErrorException ex) {
            log.error(ex.getError().toString(), ex);
        } catch (Exception ex) {
            log.error(Error.of(
                    MessagingProviderError.UNEXPECTED_ERROR.getCode(),
                    MessagingProviderError.UNEXPECTED_ERROR.getMessage(ex.getMessage())).toString(), ex);
        } finally {
            log.debug("##### LOGGER MESSAGE FILTER END #####");
        }
    }
}
