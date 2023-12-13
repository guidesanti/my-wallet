package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.common.ErrorCategory;

public class BusinessErrorException extends BaseException {

    public BusinessErrorException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public BusinessErrorException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public BusinessErrorException(String errorCode, String errorMessage, String extraDetails) {
        super(errorCode, errorMessage, extraDetails);
    }

    public BusinessErrorException(String errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorCode, errorMessage, extraDetails, cause);
    }

    @Override
    public String getErrorCategory() {
        return ErrorCategory.BUSINESS_ERROR.getValue();
    }
}
