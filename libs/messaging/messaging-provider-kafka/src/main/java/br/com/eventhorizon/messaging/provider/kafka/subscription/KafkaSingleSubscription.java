package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.kafka.KafkaMessagingProvider;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import br.com.eventhorizon.messaging.provider.subscription.SingleSubscription;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class KafkaSingleSubscription<T> implements SingleSubscription<T>, KafkaSubscription<T> {

    private final String id;

    private final String providerName = KafkaMessagingProvider.PROVIDER_NAME;

    @Singular
    private final List<MessageFilter<T>> filters;

    private final SingleMessageHandler<T> handler;

    private final String source;

    private final Class<T> sourceType;

    private final Map<String, Object> configs;
}
