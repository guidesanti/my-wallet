package br.com.eventhorizon.mywallet.common.saga.transaction;

import br.com.eventhorizon.mywallet.common.saga.SagaOption;
import br.com.eventhorizon.mywallet.common.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.mywallet.common.saga.chain.filter.SagaFilter;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;
import br.com.eventhorizon.mywallet.common.saga.handler.SagaHandler;
import br.com.eventhorizon.mywallet.common.saga.message.SagaPublisher;
import br.com.eventhorizon.mywallet.common.saga.repository.SagaRepository;
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

    @NonNull
    @Singular
    private final List<SagaContentSerializer> serializers;

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

    public List<SagaContentSerializer> serializers() {
        return serializers;
    }

    public SagaContentChecker checker() {
        return checker;
    }

    public Map<SagaOption, Object> options() {
        return options;
    }
}
