package br.com.eventhorizon.mywallet.common.common;

public final class DefaultErrors {

    public static final Error BUSINESS_RULE_NOT_MET = Error.of(ErrorCategory.BUSINESS_ERROR.getValue(), "BUSINESS_RULE_NOT_MET", "Business rule not met");

    public static final Error BAD_REQUEST = Error.of(ErrorCategory.CLIENT_ERROR.getValue(), "BAD_REQUEST", "Invalid request content format");

    public static final Error UNAUTHORIZED = Error.of(ErrorCategory.CLIENT_ERROR.getValue(), "UNAUTHORIZED", "Request not authorized");

    public static final Error FORBIDDEN = Error.of(ErrorCategory.CLIENT_ERROR.getValue(), "FORBIDDEN", "Insufficient access rights");

    public static final Error NOT_FOUND = Error.of(ErrorCategory.CLIENT_ERROR.getValue(), "NOT_FOUND", "Resource not found");

    public static final Error CONFLICT = Error.of(ErrorCategory.CLIENT_ERROR.getValue(), "CONFLICT", "Request conflicts with actual system state");

    public static final Error UNEXPECTED_SERVER_ERROR = Error.of(ErrorCategory.SERVER_ERROR.getValue(), "UNEXPECTED_SERVER_ERROR", "Unexpected internal server error (generally not retryable)");

    public static final Error TRANSIENT_SERVER_ERROR = Error.of(ErrorCategory.SERVER_ERROR.getValue(), "TRANSIENT_SERVER_ERROR", "Transient server error (retryable)");
}
