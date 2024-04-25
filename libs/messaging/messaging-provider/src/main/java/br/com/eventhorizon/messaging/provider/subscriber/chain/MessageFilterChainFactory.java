package br.com.eventhorizon.messaging.provider.subscriber.chain;

import br.com.eventhorizon.messaging.provider.subscriber.chain.filter.LoggerMessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.chain.filter.MetricsMessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MessageFilterChainFactory {

    public static <T> MessageFilterChain<T> create(Subscription<T> subscription) {
        return new MessageFilterChain<>(mergeFilters(subscription).iterator(), subscription.getHandler());
    }

    private static <T> List<MessageFilter<T>> mergeFilters(Subscription<T> subscription) {
        List<MessageFilter<T>> filters = new ArrayList<>();

        if (subscription.getFilters() != null) {
            filters.addAll(subscription.getFilters());
        }

        filters.add(new LoggerMessageFilter<>());
        filters.add(new MetricsMessageFilter<>());

        filters.sort(Comparator.comparingInt(MessageFilter::order));

        return filters;
    }
}
