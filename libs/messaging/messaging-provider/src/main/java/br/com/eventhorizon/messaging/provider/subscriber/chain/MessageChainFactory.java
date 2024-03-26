package br.com.eventhorizon.messaging.provider.subscriber.chain;

import br.com.eventhorizon.messaging.provider.subscriber.chain.filter.LoggerMessageFilter;
import br.com.eventhorizon.messaging.provider.subscription.Subscription;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MessageChainFactory {

    public static <T> MessageChain<T> create(Subscription<T> subscription) {
        return new MessageChain<>(mergeFilters(subscription).iterator(), subscription.getHandler());
    }

    private static <T> List<MessageFilter<T>> mergeFilters(Subscription<T> subscription) {
        List<MessageFilter<T>> filters = new ArrayList<>();

        if (subscription.getFilters() != null) {
            filters.addAll(subscription.getFilters());
        }

        filters.add(new LoggerMessageFilter<>());

        filters.sort(Comparator.comparingInt(MessageFilter::order));

        return filters;
    }
}
