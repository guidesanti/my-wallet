package br.com.eventhorizon.mywallet.common.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultErrorsTest {

    @Test
    public void testGetValue() {
        assertEquals("BUSINESS_RULE_NOT_MET",   DefaultErrors.BUSINESS_RULE_NOT_MET.getCode());
        assertEquals("BAD_REQUEST",             DefaultErrors.BAD_REQUEST.getCode());
        assertEquals("UNAUTHORIZED",            DefaultErrors.UNAUTHORIZED.getCode());
        assertEquals("FORBIDDEN",               DefaultErrors.FORBIDDEN.getCode());
        assertEquals("NOT_FOUND",               DefaultErrors.NOT_FOUND.getCode());
        assertEquals("CONFLICT",                DefaultErrors.CONFLICT.getCode());
        assertEquals("UNEXPECTED_SERVER_ERROR", DefaultErrors.UNEXPECTED_SERVER_ERROR.getCode());
        assertEquals("TRANSIENT_SERVER_ERROR",  DefaultErrors.TRANSIENT_SERVER_ERROR.getCode());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Business rule not met",                                       DefaultErrors.BUSINESS_RULE_NOT_MET.getMessage());
        assertEquals("Invalid request content format",                              DefaultErrors.BAD_REQUEST.getMessage());
        assertEquals("Request not authorized",                                      DefaultErrors.UNAUTHORIZED.getMessage());
        assertEquals("Insufficient access rights",                                  DefaultErrors.FORBIDDEN.getMessage());
        assertEquals("Resource not found",                                          DefaultErrors.NOT_FOUND.getMessage());
        assertEquals("Request conflicts with actual system state",                  DefaultErrors.CONFLICT.getMessage());
        assertEquals("Unexpected internal server error (generally not retryable)",  DefaultErrors.UNEXPECTED_SERVER_ERROR.getMessage());
        assertEquals("Transient server error (retryable)",                          DefaultErrors.TRANSIENT_SERVER_ERROR.getMessage());
    }
}
