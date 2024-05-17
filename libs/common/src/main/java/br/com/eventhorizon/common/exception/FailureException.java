package br.com.eventhorizon.common.exception;

public class FailureException extends RuntimeException {

    public FailureException(String message) {
        super(message);
    }

    public FailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
