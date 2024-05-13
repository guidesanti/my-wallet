package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.ErrorCategory;
import lombok.Getter;

@Getter
public abstract class BaseErrorException extends RuntimeException {

    private final Error error;

    public BaseErrorException(Error error) {
        this(error, null);
    }

    public BaseErrorException(Error error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public abstract ErrorCategory getErrorCategory();
}
