package br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.kafka.Conventions;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class DefaultKafkaBulkSubscription<T> implements KafkaBulkSubscription<T> {

    private final String id;

    private final String providerName = Conventions.PROVIDER_NAME;

    private final BulkMessageHandler<T> handler;

    private final String source;

    private final Class<T> sourceType;

    private final Map<String, Object> kafkaConsumerConfig;
}
