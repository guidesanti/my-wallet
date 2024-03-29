package br.com.eventhorizon.saga.chain.filter;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.chain.SagaChain;
import br.com.eventhorizon.saga.chain.SagaPhase;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SagaRepositoryTransactionFilter<R, M> implements SagaFilter<R, M> {

    @Override
    public int order() {
        return SagaPhase.REPOSITORY_TRANSACTION.order();
    }

    @Override
    public SagaOutput<R> filter(List<SagaMessage<M>> messages, SagaChain<R, M> chain) throws Exception {
        try {
            log.info("SAGA REPOSITORY TRANSACTION FILTER START");
            var repository = chain.repository();
            return repository.transact(() -> chain.next(messages));
        } finally {
            log.info("SAGA REPOSITORY TRANSACTION FILTER END");
        }
    }
}
