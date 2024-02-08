package br.com.eventhorizon.saga.handler;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;

import java.util.List;

public interface SagaBulkHandler<T> extends SagaHandler<T> {

    SagaOutput handle(List<SagaMessage<T>> messages) throws Exception;
}
