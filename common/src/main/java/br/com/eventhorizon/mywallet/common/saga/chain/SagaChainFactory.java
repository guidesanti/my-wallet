package br.com.eventhorizon.mywallet.common.saga.chain;

import br.com.eventhorizon.mywallet.common.saga.chain.filter.*;
import br.com.eventhorizon.mywallet.common.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.mywallet.common.saga.content.checker.impl.DefaultSagaContentChecker;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;
import br.com.eventhorizon.mywallet.common.saga.transaction.SagaTransaction;

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
                getSerializersMap(sagaTransaction),
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

    private static Map<Class<?>, SagaContentSerializer> getSerializersMap(SagaTransaction sagaTransaction) {
        Map<Class<?>, SagaContentSerializer> serializers = new HashMap<>();
        sagaTransaction.serializers().forEach(serializer -> serializers.put(serializer.getTargetClass(), serializer));
        return serializers;
    }

    private static SagaContentChecker getChecker(SagaTransaction sagaTransaction) {
        return sagaTransaction.checker() == null ? new DefaultSagaContentChecker() : sagaTransaction.checker();
    }
}
