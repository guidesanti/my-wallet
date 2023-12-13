package br.com.eventhorizon.saga.handler;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;

import java.util.List;

public interface SagaBulkHandler extends SagaHandler {

    SagaOutput handle(List<SagaMessage> messages) throws Exception;
}
