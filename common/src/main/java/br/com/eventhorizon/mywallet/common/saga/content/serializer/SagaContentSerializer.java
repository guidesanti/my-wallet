package br.com.eventhorizon.mywallet.common.saga.content.serializer;

import br.com.eventhorizon.mywallet.common.saga.content.SagaContent;

public interface SagaContentSerializer {

    Class<?> getTargetClass();

    byte[] serialize(SagaContent content);

    SagaContent deserialize(byte[] content);
}
