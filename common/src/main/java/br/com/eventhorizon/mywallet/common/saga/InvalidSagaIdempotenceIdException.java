package br.com.eventhorizon.mywallet.common.saga;

public class InvalidSagaIdempotenceIdException extends RuntimeException {

    public InvalidSagaIdempotenceIdException(String message) {
        super(message);
    }

    public InvalidSagaIdempotenceIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
