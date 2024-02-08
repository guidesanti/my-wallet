package br.com.eventhorizon.common.error;

public final class DefaultErrors {

    public static final Error BUSINESS_RULE_NOT_MET = Error.of(ErrorCode.lib("COMMON", "BUSINESS_RULE_NOT_MET"), "Business rule not met");

    public static final Error BAD_REQUEST = Error.of(ErrorCode.lib("COMMON", "BAD_REQUEST"), "Invalid request content format");

    public static final Error UNAUTHORIZED = Error.of(ErrorCode.lib("COMMON", "UNAUTHORIZED"), "Request not authorized");

    public static final Error FORBIDDEN = Error.of(ErrorCode.lib("COMMON", "FORBIDDEN"), "Insufficient access rights");

    public static final Error NOT_FOUND = Error.of(ErrorCode.lib("COMMON", "NOT_FOUND"), "Resource not found");

    public static final Error CONFLICT = Error.of(ErrorCode.lib("COMMON", "CONFLICT"), "Request conflicts with actual system state");

    public static final Error UNEXPECTED_SERVER_ERROR = Error.of(ErrorCode.lib("COMMON", "UNEXPECTED_SERVER_ERROR"), "Unexpected internal server error (a retry will probably not solve the problem)");

    public static final Error TRANSIENT_SERVER_ERROR = Error.of(ErrorCode.lib("COMMON", "TRANSIENT_SERVER_ERROR"), "Transient server error (a retry may solve the problem later)");
}
