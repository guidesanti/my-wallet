package br.com.eventhorizon.saga.messaging.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import br.com.eventhorizon.saga.transaction.SagaTransaction;

public interface SagaSubscriptionFactory {

    String getProviderName();

    <T> Subscription<T> create(SagaTransaction<T> sagaTransaction);
}
