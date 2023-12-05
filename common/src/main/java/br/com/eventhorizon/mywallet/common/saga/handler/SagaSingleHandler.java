package br.com.eventhorizon.mywallet.common.saga.handler;

import br.com.eventhorizon.mywallet.common.saga.SagaOutput;
import br.com.eventhorizon.mywallet.common.saga.SagaMessage;

public interface SagaSingleHandler extends SagaHandler {

    SagaOutput handle(SagaMessage message) throws Exception;
}
