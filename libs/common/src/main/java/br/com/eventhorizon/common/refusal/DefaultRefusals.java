package br.com.eventhorizon.common.refusal;

public final class DefaultRefusals {

    public static final Refusal BUSINESS_RULE_NOT_MET = Refusal.of(RefusalReasonCode.lib("COMMON", "BUSINESS_RULE_NOT_MET"), "Business rule not met");

    public static final Refusal BAD_REQUEST = Refusal.of(RefusalReasonCode.lib("COMMON", "BAD_REQUEST"), "Invalid request content format");

    public static final Refusal UNAUTHORIZED = Refusal.of(RefusalReasonCode.lib("COMMON", "UNAUTHORIZED"), "Request not authorized");

    public static final Refusal FORBIDDEN = Refusal.of(RefusalReasonCode.lib("COMMON", "FORBIDDEN"), "Insufficient access rights");

    public static final Refusal NOT_FOUND = Refusal.of(RefusalReasonCode.lib("COMMON", "NOT_FOUND"), "Resource not found");

    public static final Refusal CONFLICT = Refusal.of(RefusalReasonCode.lib("COMMON", "CONFLICT"), "Request conflicts with actual system state");

    public static final Refusal UNEXPECTED_SERVER_ERROR = Refusal.of(RefusalReasonCode.lib("COMMON", "UNEXPECTED_SERVER_ERROR"), "Unexpected internal server error (a retry will probably not solve the problem)");

    public static final Refusal TRANSIENT_SERVER_ERROR = Refusal.of(RefusalReasonCode.lib("COMMON", "TRANSIENT_SERVER_ERROR"), "Transient server error (a retry may solve the problem later)");
}
