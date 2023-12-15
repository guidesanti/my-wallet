package br.com.eventhorizon.saga.content.serialization;

import br.com.eventhorizon.saga.content.SagaContent;

public interface SagaContentSerializer {

    byte[] serialize(SagaContent content);

    <T> SagaContent deserialize(byte[] content, Class<T> type);
}
