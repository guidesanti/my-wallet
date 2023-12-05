package br.com.eventhorizon.mywallet.common.saga.message;

import br.com.eventhorizon.mywallet.common.saga.SagaEvent;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;
import br.com.eventhorizon.mywallet.common.transaction.TransactionManager;

import java.util.Map;

public interface SagaPublisher extends TransactionManager {

    void publish(SagaEvent event, Map<Class<?>, SagaContentSerializer> serializers);
}
