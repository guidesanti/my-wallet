package br.com.eventhorizon.mywallet.common.saga.handler;

import br.com.eventhorizon.mywallet.common.saga.SagaOutput;
import br.com.eventhorizon.mywallet.common.saga.SagaMessage;

import java.util.List;

public interface SagaBulkHandler extends SagaHandler {

    SagaOutput handle(List<SagaMessage> messages) throws Exception;
}
