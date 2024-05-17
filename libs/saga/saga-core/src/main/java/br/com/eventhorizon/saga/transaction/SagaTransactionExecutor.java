package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.common.exception.RefusedException;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaResponse;
import br.com.eventhorizon.saga.chain.SagaChainFactory;

import java.util.List;

public class SagaTransactionExecutor {

    public <R, M> SagaResponse<R> execute(SagaTransaction<R, M> transaction, SagaMessage<M> message) {
        return execute(transaction, List.of(message));
    }

    public <R, M> SagaResponse<R> execute(SagaTransaction<R, M> transaction, List<SagaMessage<M>> messages) {
        try {
            var chain = SagaChainFactory.create(transaction);
            return chain.next(messages).response();
        } catch (RefusedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
