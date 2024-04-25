package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.common.runtime.ApplicationLifecycleListener;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.SubscriptionManager;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SagaSubscriptionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SagaTransactionManager implements ApplicationLifecycleListener {

    private final SubscriptionManager subscriptionManager;

    private final Map<String, SagaSubscriptionFactory> factories;

    private final List<SagaTransaction<?, ?>> transactions;

    public SagaTransactionManager(SubscriptionManager subscriptionManager, List<SagaSubscriptionFactory> factories, List<SagaTransaction<?, ?>> transactions) {
        this.subscriptionManager = subscriptionManager;
        this.factories = factories.stream().collect(Collectors.toMap(SagaSubscriptionFactory::getProviderName, Function.identity()));
        this.transactions = transactions;
    }

    @Override
    public void onStarting() {
        // Do nothing
    }

    @Override
    public void onStarted() {
        log.info("==================================== Creating subscriptions for SAGA transactions ====================================");
        transactions.forEach(transaction -> {
            log.info("Creating subscription for SAGA transaction: {}", transaction.getId());
            var factory = factories.get(transaction.getMessagingProviderName());
            checkFactory(transaction, factory);
            var subscription = factory.create(transaction);
            subscriptionManager.subscribe(subscription);
        });
    }

    @Override
    public void onReady() {
        // Do nothing
    }

    @Override
    public void onShuttingDown() {
        // Do nothing
    }

    private void checkFactory(SagaTransaction<?, ?> transaction, SagaSubscriptionFactory factory) {
        if (factory == null) {
            var message = String.format(
                    "Cannot find factory for provider name '%s', available providers are: %s",
                    transaction.getMessagingProviderName(),
                    factories.values().stream().map(SagaSubscriptionFactory::getProviderName).toList());
            log.error(message);
            throw new RuntimeException(message);
        }
    }
}
