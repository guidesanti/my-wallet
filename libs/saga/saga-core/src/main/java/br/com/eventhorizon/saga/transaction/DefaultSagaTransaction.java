package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.saga.SagaOption;
import br.com.eventhorizon.saga.chain.filter.SagaFilter;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
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
public class DefaultSagaTransaction<T> implements SagaTransaction<T> {

    @NonNull
    protected final String id;

    protected final String messagingProviderName;

    protected final String source;

    protected final Class<T> sourceType;

    @NonNull
    protected final SagaHandler handler;

    @NonNull
    protected final SagaRepository repository;

    @NonNull
    protected final SagaPublisher publisher;

    @Singular
    protected final List<SagaFilter> filters;

    protected final SagaContentSerializer serializer;

    protected final SagaContentChecker checker;

    @Singular
    protected final Map<SagaOption, Object> options;
}
