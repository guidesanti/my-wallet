package br.com.eventhorizon.saga.chain;

import br.com.eventhorizon.saga.content.checker.impl.DefaultSagaContentChecker;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.saga.content.serialization.impl.DefaultSagaContentSerializer;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.chain.filter.*;

import java.util.*;

public final class SagaChainFactory {

    public static SagaChain create(SagaTransaction sagaTransaction) {
        var options = SagaOptions.of(sagaTransaction.options());
        sagaTransaction.repository().configure(options);
        return new SagaChain(mergeFilters(sagaTransaction).iterator(),
                sagaTransaction.handler(),
                sagaTransaction.repository(),
                sagaTransaction.publisher(),
                getChecker(sagaTransaction),
                getSerializer(sagaTransaction),
                options);
    }

    private static List<SagaFilter> mergeFilters(SagaTransaction sagaTransaction) {
        List<SagaFilter> filters = new ArrayList<>();

        if (sagaTransaction.filters() != null && !sagaTransaction.filters().isEmpty()) {
            filters.addAll(sagaTransaction.filters());
        }

        filters.add(new SagaLoggerFilter());
        filters.add(new SagaIdempotenceFilter());
        filters.add(new SagaRepositoryTransactionFilter());
        filters.add(new SagaBrokerTransactionFilter());
        filters.add(new SagaEventPublisherFilter());

        filters.sort(Comparator.comparingInt(SagaFilter::order));

        return filters;
    }

    private static SagaContentSerializer getSerializer(SagaTransaction sagaTransaction) {
        return sagaTransaction.serializer() == null ? new DefaultSagaContentSerializer() : sagaTransaction.serializer();
    }

    private static SagaContentChecker getChecker(SagaTransaction sagaTransaction) {
        return sagaTransaction.checker() == null ? new DefaultSagaContentChecker() : sagaTransaction.checker();
    }
}
