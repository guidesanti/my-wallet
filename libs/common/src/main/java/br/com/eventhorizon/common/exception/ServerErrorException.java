package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.common.ErrorCategory;

public class ServerErrorException extends BaseException {

    public ServerErrorException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public ServerErrorException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public ServerErrorException(String errorCode, String errorMessage, String extraDetails) {
        super(errorCode, errorMessage, extraDetails);
    }

    public ServerErrorException(String errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorCode, errorMessage, extraDetails, cause);
    }

    @Override
    public String getErrorCategory() {
        return ErrorCategory.SERVER_ERROR.getValue();
    }
}
