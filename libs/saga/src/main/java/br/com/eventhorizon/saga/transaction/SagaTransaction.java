package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.saga.chain.filter.SagaFilter;
import br.com.eventhorizon.saga.SagaOption;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.saga.handler.SagaHandler;
import br.com.eventhorizon.saga.messaging.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaTransaction {

    @NonNull
    private final SagaHandler handler;

    @NonNull
    private final SagaRepository repository;

    @NonNull
    private final SagaPublisher publisher;

    @Singular
    private final List<SagaFilter> filters;

    private final SagaContentSerializer serializer;

    private final SagaContentChecker checker;

    @Singular
    private final Map<SagaOption, Object> options;

    public SagaHandler handler() {
        return handler;
    }

    public SagaRepository repository() {
        return repository;
    }

    public SagaPublisher publisher() {
        return publisher;
    }

    public List<SagaFilter> filters() {
        return filters;
    }

    public SagaContentSerializer serializer() {
        return serializer;
    }

    public SagaContentChecker checker() {
        return checker;
    }

    public Map<SagaOption, Object> options() {
        return options;
    }
}
