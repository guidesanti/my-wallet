package br.com.eventhorizon.saga.content.checker;

import br.com.eventhorizon.saga.SagaMessage;

public interface SagaContentChecker<M> {

    String checksum(SagaMessage<M> message);
}
