package br.com.eventhorizon.saga.content.checker;

public class SagaCheckerException extends RuntimeException {

    public SagaCheckerException(String message) {
        super(message);
    }

    public SagaCheckerException(String message, Throwable cause) {
        super(message, cause);
    }
}
