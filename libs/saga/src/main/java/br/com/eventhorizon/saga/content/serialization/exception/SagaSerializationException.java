package br.com.eventhorizon.saga.content.serialization.exception;

public class SagaSerializationException extends RuntimeException {

    public SagaSerializationException(String message) {
        super(message);
    }

    public SagaSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
