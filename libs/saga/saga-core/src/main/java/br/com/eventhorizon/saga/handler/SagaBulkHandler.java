package br.com.eventhorizon.saga.handler;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;

import java.util.List;

public interface SagaBulkHandler<R, M> extends SagaHandler<R, M> {

    SagaOutput<R> handle(List<SagaMessage<M>> messages) throws Exception;
}
