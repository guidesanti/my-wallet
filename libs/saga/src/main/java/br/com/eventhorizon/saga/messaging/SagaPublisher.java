package br.com.eventhorizon.saga.messaging;

import br.com.eventhorizon.saga.SagaEvent;
import br.com.eventhorizon.saga.content.serializer.SagaContentSerializer;
import br.com.eventhorizon.common.transaction.TransactionManager;

import java.util.Map;

public interface SagaPublisher extends TransactionManager {

    void publish(SagaEvent event, Map<Class<?>, SagaContentSerializer> serializers);
}
