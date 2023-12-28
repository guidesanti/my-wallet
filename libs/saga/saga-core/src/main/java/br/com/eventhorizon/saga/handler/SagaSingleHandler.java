package br.com.eventhorizon.saga.handler;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;

public interface SagaSingleHandler extends SagaHandler {

    SagaOutput handle(SagaMessage message) throws Exception;
}
