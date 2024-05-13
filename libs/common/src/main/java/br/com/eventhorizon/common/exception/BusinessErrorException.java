package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.ErrorCategory;

public class BusinessErrorException extends BaseErrorException {

    public BusinessErrorException(Error error) {
        super(error);
    }

    public BusinessErrorException(Error error, Throwable cause) {
        super(error, cause);
    }

    @Override
    public ErrorCategory getErrorCategory() {
        return ErrorCategory.BUSINESS_ERROR;
    }
}
