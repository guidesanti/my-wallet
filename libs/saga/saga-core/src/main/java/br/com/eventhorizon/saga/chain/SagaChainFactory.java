package br.com.eventhorizon.saga.chain;

import br.com.eventhorizon.saga.chain.filter.*;
import br.com.eventhorizon.saga.content.checker.impl.DefaultSagaContentChecker;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.saga.content.serialization.impl.DefaultSagaContentSerializer;
import br.com.eventhorizon.saga.transaction.SagaTransaction;

import java.util.*;

public final class SagaChainFactory {

    public static <T> SagaChain<T> create(SagaTransaction<T> sagaTransaction) {
        var options = SagaOptions.of(sagaTransaction.getOptions());
        sagaTransaction.getRepository().configure(options);
        return new SagaChain<>(mergeFilters(sagaTransaction).iterator(),
                sagaTransaction.getHandler(),
                sagaTransaction.getRepository(),
                sagaTransaction.getPublisher(),
                getChecker(sagaTransaction),
                getSerializer(sagaTransaction),
                options);
    }

    private static <T> List<SagaFilter<T>> mergeFilters(SagaTransaction<T> sagaTransaction) {
        List<SagaFilter<T>> filters = new ArrayList<>();

        if (sagaTransaction.getFilters() != null && !sagaTransaction.getFilters().isEmpty()) {
            filters.addAll(sagaTransaction.getFilters());
        }

        filters.add(new SagaLoggerFilter<>());
        filters.add(new SagaIdempotenceFilter<>());
        filters.add(new SagaRepositoryTransactionFilter<>());
        filters.add(new SagaBrokerTransactionFilter<>());
        filters.add(new SagaEventPublisherFilter<>());

        filters.sort(Comparator.comparingInt(SagaFilter::order));

        return filters;
    }

    private static <T> SagaContentSerializer getSerializer(SagaTransaction<T> sagaTransaction) {
        return sagaTransaction.getSerializer() == null ? new DefaultSagaContentSerializer() : sagaTransaction.getSerializer();
    }

    private static <T> SagaContentChecker<T> getChecker(SagaTransaction<T> sagaTransaction) {
        return sagaTransaction.getChecker() == null ? new DefaultSagaContentChecker<>() : sagaTransaction.getChecker();
    }
}
