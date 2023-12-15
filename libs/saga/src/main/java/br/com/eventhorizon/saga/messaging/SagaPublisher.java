package br.com.eventhorizon.saga.messaging;

import br.com.eventhorizon.saga.SagaEvent;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.common.transaction.TransactionManager;

public interface SagaPublisher extends TransactionManager {

    void publish(SagaEvent event, SagaContentSerializer serializer);
}
