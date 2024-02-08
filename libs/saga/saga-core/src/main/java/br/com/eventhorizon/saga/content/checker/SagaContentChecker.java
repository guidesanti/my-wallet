package br.com.eventhorizon.saga.content.checker;

import br.com.eventhorizon.saga.SagaMessage;

public interface SagaContentChecker<T> {

    String checksum(SagaMessage<T> message);
}
