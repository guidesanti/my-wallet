package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.error.ErrorCode;

public abstract class BaseErrorException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String extraDetails;

    public BaseErrorException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.extraDetails = null;
    }

    public BaseErrorException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.extraDetails = null;
    }

    public BaseErrorException(ErrorCode errorCode, String errorMessage, String extraDetails) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.extraDetails = extraDetails;
    }

    public BaseErrorException(ErrorCode errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.extraDetails = extraDetails;
    }

    public abstract ErrorCategory getErrorCategory();

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return getMessage();
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public Error getError() {
        return Error.of(getErrorCode(), getErrorMessage(), getExtraDetails());
    }
}
