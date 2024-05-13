package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.ErrorCategory;

public class ServerErrorException extends BaseErrorException {

    public ServerErrorException(Error error) {
        super(error);
    }

    public ServerErrorException(Error error, Throwable cause) {
        super(error, cause);
    }

    @Override
    public ErrorCategory getErrorCategory() {
        return ErrorCategory.SERVER_ERROR;
    }
}
