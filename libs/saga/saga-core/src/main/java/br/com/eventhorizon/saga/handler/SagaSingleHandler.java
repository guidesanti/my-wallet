package br.com.eventhorizon.saga.handler;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;

public interface SagaSingleHandler<R, M> extends SagaHandler<R, M> {

    SagaOutput<R> handle(SagaMessage<M> message) throws Exception;
}
