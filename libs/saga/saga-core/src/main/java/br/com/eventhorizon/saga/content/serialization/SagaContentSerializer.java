package br.com.eventhorizon.saga.content.serialization;

public interface SagaContentSerializer {

    <T> byte[] serialize(T content);

    <T> T deserialize(byte[] content, Class<T> type);
}
