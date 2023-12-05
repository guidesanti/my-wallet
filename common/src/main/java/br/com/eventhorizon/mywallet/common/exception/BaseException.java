package br.com.eventhorizon.mywallet.common.exception;

import br.com.eventhorizon.mywallet.common.common.Error;

public abstract class BaseException extends RuntimeException {

    private final String errorCode;

    private final String extraDetails;

    public BaseException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.extraDetails = null;
    }

    public BaseException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.extraDetails = null;
    }

    public BaseException(String errorCode, String errorMessage, String extraDetails) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.extraDetails = extraDetails;
    }

    public BaseException(String errorCode, String errorMessage, String extraDetails, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.extraDetails = extraDetails;
    }

    public abstract String getErrorCategory();

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return getMessage();
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public Error getError() {
        return Error.of(getErrorCategory(), getErrorCode(), getErrorMessage(), getExtraDetails());
    }
}
