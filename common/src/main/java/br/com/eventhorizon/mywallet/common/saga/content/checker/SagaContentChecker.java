package br.com.eventhorizon.mywallet.common.saga.content.checker;

import br.com.eventhorizon.mywallet.common.saga.SagaMessage;

public interface SagaContentChecker {

    String checksum(SagaMessage message);
}
