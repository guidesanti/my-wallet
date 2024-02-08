package br.com.eventhorizon.saga.handler;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;

public interface SagaSingleHandler<T> extends SagaHandler<T> {

    SagaOutput handle(SagaMessage<T> message) throws Exception;
}
