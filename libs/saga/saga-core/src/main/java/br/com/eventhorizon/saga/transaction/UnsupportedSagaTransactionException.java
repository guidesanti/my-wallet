package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.saga.messaging.subscriber.subscription.SagaSubscriptionFactory;

public class UnsupportedSagaTransactionException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Transaction of type '%s' is not supported by factory class '%s'";

    public UnsupportedSagaTransactionException(SagaSubscriptionFactory factory, SagaTransaction<?> sagaTransaction) {
        super(String.format(MESSAGE_TEMPLATE, sagaTransaction.getClass(), factory.getClass()));
    }
}
