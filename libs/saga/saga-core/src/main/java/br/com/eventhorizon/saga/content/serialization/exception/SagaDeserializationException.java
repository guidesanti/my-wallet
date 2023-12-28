package br.com.eventhorizon.saga.content.serialization.exception;

public class SagaDeserializationException extends RuntimeException {

    public SagaDeserializationException(String message) {
        super(message);
    }

    public SagaDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
