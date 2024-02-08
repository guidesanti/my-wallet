package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.error.ErrorCode;

public class BusinessErrorErrorException extends BaseErrorException {

    public BusinessErrorErrorException(ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public BusinessErrorErrorException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public BusinessErrorErrorException(ErrorCode errorCode, String errorMessage, String extraDetails) {
        super(errorCode, errorMessage, extraDetails);
    }

    public BusinessErrorErrorException(ErrorCode errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorCode, errorMessage, extraDetails, cause);
    }

    @Override
    public ErrorCategory getErrorCategory() {
        return ErrorCategory.BUSINESS_ERROR;
    }
}
