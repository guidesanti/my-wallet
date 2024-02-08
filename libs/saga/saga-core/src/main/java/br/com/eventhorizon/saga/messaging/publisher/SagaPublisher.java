package br.com.eventhorizon.saga.messaging.publisher;

import br.com.eventhorizon.saga.SagaEvent;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.common.transaction.TransactionManager;

public interface SagaPublisher extends TransactionManager {

    <T> void publish(SagaEvent<T> event, SagaContentSerializer serializer);
}
