package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.saga.SagaOption;
import br.com.eventhorizon.saga.chain.filter.SagaFilter;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.checker.impl.DefaultSagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.saga.content.serialization.impl.DefaultSagaContentSerializer;
import br.com.eventhorizon.saga.handler.SagaHandler;
import br.com.eventhorizon.saga.messaging.publisher.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SagaTransaction<T> {

    @NonNull
    protected final String id;

    @NonNull
    protected final String messagingProviderName;

    @NonNull
    protected final String source;

    @NonNull
    protected final Class<T> sourceType;

    protected final String dlq;

    @NonNull
    protected final SagaHandler<T> handler;

    @NonNull
    protected final SagaRepository repository;

    @NonNull
    protected final SagaPublisher publisher;

    @Singular
    protected final List<SagaFilter<T>> filters;

    @NonNull
    @Builder.Default
    protected final SagaContentSerializer serializer = new DefaultSagaContentSerializer();

    @NonNull
    @Builder.Default
    protected final SagaContentChecker<T> checker = new DefaultSagaContentChecker<>();

    @Singular
    protected final Map<SagaOption, Object> options;
}
