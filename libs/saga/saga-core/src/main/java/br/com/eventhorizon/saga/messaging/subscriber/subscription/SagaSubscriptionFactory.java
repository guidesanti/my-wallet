package br.com.eventhorizon.saga.messaging.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import br.com.eventhorizon.saga.transaction.SagaTransaction;

public interface SagaSubscriptionFactory {

    String getProviderName();

    <R, M> Subscription<M> create(SagaTransaction<R, M> sagaTransaction);
}
