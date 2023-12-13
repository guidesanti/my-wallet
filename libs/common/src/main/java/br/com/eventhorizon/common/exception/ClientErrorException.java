package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.common.ErrorCategory;

public class ClientErrorException extends BaseException {

    public ClientErrorException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public ClientErrorException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public ClientErrorException(String errorCode, String errorMessage, String extraDetails) {
        super(errorCode, errorMessage, extraDetails);
    }

    public ClientErrorException(String errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorCode, errorMessage, extraDetails, cause);
    }

    @Override
    public String getErrorCategory() {
        return ErrorCategory.CLIENT_ERROR.getValue();
    }
}
