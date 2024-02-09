package br.com.eventhorizon.saga.repository;

import br.com.eventhorizon.saga.SagaEvent;
import br.com.eventhorizon.saga.SagaResponse;
import br.com.eventhorizon.saga.chain.SagaOptions;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.common.transaction.TransactionManager;

import java.util.List;

public interface SagaRepository extends TransactionManager {

    boolean createTransaction(String idempotenceId, String checksum);

    SagaTransactionEntity findTransaction(String idempotenceId);

    void createResponse(SagaResponse<?> response, SagaContentSerializer serializer);

    <R> SagaResponse<R> findResponse(String idempotenceId, SagaContentSerializer serializer);

    void createEvents(List<SagaEvent<?>> events, SagaContentSerializer serializer);

    List<SagaEvent<Object>> findEvents(String originalIdempotenceId, SagaContentSerializer serializer);

    void incrementEventPublishCount(String eventId);

    void configure(SagaOptions options);
}
