package br.com.eventhorizon.messaging.provider.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.handler.MessageHandler;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public class BaseSubscription<T> implements Subscription<T> {

    private final String id;

    private final String providerName;

    @Singular
    private final List<MessageFilter<T>> filters;

    private final MessageHandler<T> handler;

    private final String source;

    private final Class<T> sourceType;

    @Builder.Default
    private final SubscriptionProperties properties = new SubscriptionProperties();
}
