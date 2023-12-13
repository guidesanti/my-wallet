package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaResponse;
import br.com.eventhorizon.saga.chain.SagaChain;
import br.com.eventhorizon.saga.chain.SagaChainFactory;
import br.com.eventhorizon.common.exception.BaseException;

import java.util.List;

public class SagaTransactionManager {

    public SagaResponse execute(SagaTransaction transaction, SagaMessage message) {
        return execute(transaction, List.of(message));
    }

    public SagaResponse execute(SagaTransaction transaction, List<SagaMessage> messages) {
        try {
            SagaChain chain = SagaChainFactory.create(transaction);
            return chain.next(messages).response();
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
