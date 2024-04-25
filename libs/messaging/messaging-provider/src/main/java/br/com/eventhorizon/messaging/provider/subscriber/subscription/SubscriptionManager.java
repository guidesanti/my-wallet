package br.com.eventhorizon.messaging.provider.subscriber.subscription;

import br.com.eventhorizon.common.runtime.ApplicationLifecycleListener;
import br.com.eventhorizon.messaging.provider.subscriber.Subscriber;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberFactory;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMonitor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SubscriptionManager implements ApplicationLifecycleListener {

    private final ExecutorService executorService;

    private final List<Subscription<?>> subscriptions;

    private final Map<String, SubscriberFactory> factories;

    private final List<Subscriber<?>> subscribers;

    private final SubscriberMonitor subscriberMonitor;

    public SubscriptionManager(ExecutorService executorService, List<Subscription<?>> subscriptions, List<SubscriberFactory> factories) {
        this.executorService = executorService;
        this.subscriptions = subscriptions;
        this.factories = factories.stream().collect(Collectors.toMap(SubscriberFactory::getProviderName, Function.identity()));
        this.subscribers = Collections.synchronizedList(new ArrayList<>());
        this.subscriberMonitor = new SubscriberMonitor(this.subscribers);
    }

    public <T> void subscribe(Subscription<T> subscription) {
        createAndStartSubscriber(subscription);
    }

    @Override
    public void onStarting() {
        // Do nothing
    }

    @Override
    public void onStarted() {
        log.info("==================================== Starting subscribers ====================================");
        subscriptions.forEach(this::createAndStartSubscriber);
        subscriberMonitor.startMonitoring();
    }

    @Override
    public void onReady() {
        // Do nothing
    }

    @Override
    public void onShuttingDown() {
        log.info("==================================== Stopping subscribers ====================================");
        subscriberMonitor.stopMonitoring();
        subscribers.forEach(subscriber -> {
            subscriber.stop();
            log.warn("Subscriber successfully stopped: {}", subscriber);
        });
    }

    private void checkFactory(Subscription<?> subscription, SubscriberFactory factory) {
        if (factory == null) {
            var message = String.format(
                    "Cannot find factory for provider name '%s', available providers are: %s",
                    subscription.getProviderName(),
                    factories.values().stream().map(SubscriberFactory::getProviderName).toList());
            log.error(message);
            throw new RuntimeException(message);
        }
    }

    private void createAndStartSubscriber(Subscription<?> subscription) {
        executorService.execute(() -> {
            log.info("Creating and starting subscriber for subscription: {}", subscription.getId());
            var factory = factories.get(subscription.getProviderName());
            checkFactory(subscription, factory);
            long concurrency = subscription.getProperties().getPropertyValue(SubscriptionProperties.CONCURRENCY);
            for (int i = 0; i < concurrency; i++) {
                var subscriber = factory.create(subscription);
                subscriber.start();
                subscribers.add(subscriber);
            }
        });
    }
}
