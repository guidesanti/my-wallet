package br.com.eventhorizon.messaging.provider.kafka.subscription;

import br.com.eventhorizon.messaging.provider.kafka.KafkaMessagingProvider;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import br.com.eventhorizon.messaging.provider.subscription.BulkSubscription;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class KafkaBulkSubscription<T> implements BulkSubscription<T>, KafkaSubscription<T> {

    private final String id;

    private final String providerName = KafkaMessagingProvider.PROVIDER_NAME;

    private final List<MessageFilter<T>> filters;

    private final BulkMessageHandler<T> handler;

    private final String source;

    private final Class<T> sourceType;

    private final Map<String, Object> configs;
}
