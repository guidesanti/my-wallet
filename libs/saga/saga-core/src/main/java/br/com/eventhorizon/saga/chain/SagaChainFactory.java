package br.com.eventhorizon.saga.chain;

import br.com.eventhorizon.saga.chain.filter.*;
import br.com.eventhorizon.saga.content.checker.MD5SumSagaContentChecker;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.saga.content.serialization.impl.DefaultSagaContentSerializer;
import br.com.eventhorizon.saga.transaction.SagaTransaction;

import java.util.*;

public final class SagaChainFactory {

    public static <R, M> SagaChain<R, M> create(SagaTransaction<R, M> sagaTransaction) {
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

    private static <R, M> List<SagaFilter<R, M>> mergeFilters(SagaTransaction<R, M> sagaTransaction) {
        List<SagaFilter<R, M>> filters = new ArrayList<>();

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

    private static <R, M> SagaContentSerializer getSerializer(SagaTransaction<R, M> sagaTransaction) {
        return sagaTransaction.getSerializer() == null ? new DefaultSagaContentSerializer() : sagaTransaction.getSerializer();
    }

    private static <R, M> SagaContentChecker<M> getChecker(SagaTransaction<R, M> sagaTransaction) {
        return sagaTransaction.getChecker() == null ? new MD5SumSagaContentChecker<>() : sagaTransaction.getChecker();
    }
}
