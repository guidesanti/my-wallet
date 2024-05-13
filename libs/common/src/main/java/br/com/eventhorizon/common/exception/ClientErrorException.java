package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.ErrorCategory;

public class ClientErrorException extends BaseErrorException {

    public ClientErrorException(Error error) {
        super(error);
    }

    public ClientErrorException(Error error, Throwable cause) {
        super(error, cause);
    }

    @Override
    public ErrorCategory getErrorCategory() {
        return ErrorCategory.CLIENT_ERROR;
    }
}
