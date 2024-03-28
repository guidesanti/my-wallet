package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.error.ErrorCode;

public class BusinessErrorException extends BaseErrorException {

    public BusinessErrorException(ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public BusinessErrorException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public BusinessErrorException(ErrorCode errorCode, String errorMessage, String extraDetails) {
        super(errorCode, errorMessage, extraDetails);
    }

    public BusinessErrorException(ErrorCode errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorCode, errorMessage, extraDetails, cause);
    }

    @Override
    public ErrorCategory getErrorCategory() {
        return ErrorCategory.BUSINESS_ERROR;
    }
}
