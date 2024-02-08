package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.error.ErrorCode;

public class ClientErrorErrorException extends BaseErrorException {

    public ClientErrorErrorException(ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public ClientErrorErrorException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public ClientErrorErrorException(ErrorCode errorCode, String errorMessage, String extraDetails) {
        super(errorCode, errorMessage, extraDetails);
    }

    public ClientErrorErrorException(ErrorCode errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorCode, errorMessage, extraDetails, cause);
    }

    @Override
    public ErrorCategory getErrorCategory() {
        return ErrorCategory.CLIENT_ERROR;
    }
}
