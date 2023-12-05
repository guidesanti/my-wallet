package br.com.eventhorizon.mywallet.common.saga.transaction;

import br.com.eventhorizon.mywallet.common.exception.BaseException;
import br.com.eventhorizon.mywallet.common.saga.SagaResponse;
import br.com.eventhorizon.mywallet.common.saga.SagaMessage;
import br.com.eventhorizon.mywallet.common.saga.chain.SagaChain;
import br.com.eventhorizon.mywallet.common.saga.chain.SagaChainFactory;

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
