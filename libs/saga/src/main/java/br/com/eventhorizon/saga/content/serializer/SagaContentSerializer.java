package br.com.eventhorizon.saga.content.serializer;

import br.com.eventhorizon.saga.content.SagaContent;

public interface SagaContentSerializer {

    Class<?> getTargetClass();

    byte[] serialize(SagaContent content);

    SagaContent deserialize(byte[] content);
}
