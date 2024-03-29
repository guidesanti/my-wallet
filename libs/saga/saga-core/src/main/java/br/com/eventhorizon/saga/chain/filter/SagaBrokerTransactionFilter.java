package br.com.eventhorizon.saga.chain.filter;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.chain.SagaChain;
import br.com.eventhorizon.saga.chain.SagaPhase;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SagaBrokerTransactionFilter<R, M> implements SagaFilter<R, M> {

    @Override
    public int order() {
        return SagaPhase.BROKER_TRANSACTION.order();
    }

    @Override
    public SagaOutput<R> filter(List<SagaMessage<M>> messages, SagaChain<R, M> chain) throws Exception {
        try {
            log.info("SAGA BROKER TRANSACTION FILTER START");
            var publisher = chain.publisher();
            return publisher.transact(() -> chain.next(messages));
        } finally {
            log.info("SAGA BROKER TRANSACTION FILTER END");
        }
    }
}
