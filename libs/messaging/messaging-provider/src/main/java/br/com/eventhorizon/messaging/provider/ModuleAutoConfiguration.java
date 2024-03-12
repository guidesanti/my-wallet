package br.com.eventhorizon.messaging.provider;

import br.com.eventhorizon.common.runtime.impl.ApplicationLifecycleDispatcher;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberFactory;
import br.com.eventhorizon.messaging.provider.subscription.Subscription;
import br.com.eventhorizon.messaging.provider.subscription.SubscriptionManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.ExecutorService;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public SubscriptionManager subscriptionManager(ExecutorService executorService,
                                                   List<Subscription<?>> subscriptions,
                                                   ApplicationLifecycleDispatcher applicationLifecycleDispatcher,
                                                   List<SubscriberFactory> factories) {
        var subscriptionManager = new SubscriptionManager(executorService, subscriptions, factories);
        applicationLifecycleDispatcher.addListener(subscriptionManager);
        return subscriptionManager;
    }
}
