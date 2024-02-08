package br.com.eventhorizon.saga;

import br.com.eventhorizon.common.runtime.impl.ApplicationLifecycleDispatcher;
import br.com.eventhorizon.messaging.provider.publisher.TransactionablePublisher;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.SubscriptionManager;
import br.com.eventhorizon.saga.messaging.publisher.DefaultSagaPublisher;
import br.com.eventhorizon.saga.messaging.publisher.SagaPublisher;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SagaSubscriptionFactory;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import br.com.eventhorizon.saga.transaction.SagaTransactionManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public SagaTransactionManager sagaTransactionManager(
            ApplicationLifecycleDispatcher applicationLifecycleDispatcher,
            SubscriptionManager subscriptionManager,
            List<SagaSubscriptionFactory> factories,
            List<SagaTransaction<?>> transactions) {
        var sagaTransactionManager = new SagaTransactionManager(subscriptionManager, factories, transactions);
        applicationLifecycleDispatcher.addListener(sagaTransactionManager);
        return sagaTransactionManager;
    }

    @Bean
    public SagaTransactionExecutor sagaTransactionExecutor() {
        return new SagaTransactionExecutor();
    }

    @Bean
    public SagaPublisher sagaPublisher(TransactionablePublisher transactionablePublisher) {
        return new DefaultSagaPublisher(transactionablePublisher);
    }
}
