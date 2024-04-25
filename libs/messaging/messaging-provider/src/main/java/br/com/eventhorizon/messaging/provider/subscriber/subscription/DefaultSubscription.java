package br.com.eventhorizon.messaging.provider.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.handler.MessageHandler;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public abstract class DefaultSubscription<T> implements Subscription<T> {

    private final String id;

    @NonNull
    @Singular
    private final List<MessageFilter<T>> filters;

    @NonNull
    private final MessageHandler<T> handler;

    @NonNull
    private final String source;

    @NonNull
    private final Class<T> sourceType;

    @NonNull
    @Builder.Default
    private final SubscriptionProperties properties = new SubscriptionProperties();
}
