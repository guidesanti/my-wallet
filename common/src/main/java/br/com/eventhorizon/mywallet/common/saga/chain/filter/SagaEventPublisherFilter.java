package br.com.eventhorizon.mywallet.common.saga.chain.filter;

import br.com.eventhorizon.mywallet.common.saga.SagaMessage;
import br.com.eventhorizon.mywallet.common.saga.SagaOption;
import br.com.eventhorizon.mywallet.common.saga.SagaOutput;
import br.com.eventhorizon.mywallet.common.saga.chain.SagaChain;
import br.com.eventhorizon.mywallet.common.saga.chain.SagaPhase;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SagaEventPublisherFilter implements SagaFilter {

    @Override
    public int order() {
        return SagaPhase.BROKER_TRANSACTION.after();
    }

    @Override
    public SagaOutput filter(List<SagaMessage> messages, SagaChain chain) throws Exception {
        try {
            log.info("SAGA EVENT PUBLISHER FILTER START");
            var repository = chain.repository();
            var publisher = chain.publisher();
            var serdes = chain.serializers();
            var output = chain.next(messages);
            for (var event : output.events()) {
                if (event.publishCount() == 0 || Boolean.TRUE == chain.options().get(SagaOption.EVENT_REPUBLISH_ENABLED)) {
                    publisher.publish(event, serdes);
                    repository.incrementEventPublishCount(event.id());
                }
            }
            return output;
        } finally {
            log.info("SAGA EVENT PUBLISHER FILTER END");
        }
    }
}
