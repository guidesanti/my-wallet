package br.com.eventhorizon.common.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultErrorsTest {

    @Test
    public void testGetCode() {
        assertEquals(ErrorCode.lib("COMMON", "BUSINESS_RULE_NOT_MET"),   DefaultErrors.BUSINESS_RULE_NOT_MET.getCode());
        assertEquals(ErrorCode.lib("COMMON", "BAD_REQUEST"),             DefaultErrors.BAD_REQUEST.getCode());
        assertEquals(ErrorCode.lib("COMMON", "UNAUTHORIZED"),            DefaultErrors.UNAUTHORIZED.getCode());
        assertEquals(ErrorCode.lib("COMMON", "FORBIDDEN"),               DefaultErrors.FORBIDDEN.getCode());
        assertEquals(ErrorCode.lib("COMMON", "NOT_FOUND"),               DefaultErrors.NOT_FOUND.getCode());
        assertEquals(ErrorCode.lib("COMMON", "CONFLICT"),                DefaultErrors.CONFLICT.getCode());
        assertEquals(ErrorCode.lib("COMMON", "UNEXPECTED_SERVER_ERROR"), DefaultErrors.UNEXPECTED_SERVER_ERROR.getCode());
        assertEquals(ErrorCode.lib("COMMON", "TRANSIENT_SERVER_ERROR"),  DefaultErrors.TRANSIENT_SERVER_ERROR.getCode());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Business rule not met",                                                           DefaultErrors.BUSINESS_RULE_NOT_MET.getMessage());
        assertEquals("Invalid request content format",                                                  DefaultErrors.BAD_REQUEST.getMessage());
        assertEquals("Request not authorized",                                                          DefaultErrors.UNAUTHORIZED.getMessage());
        assertEquals("Insufficient access rights",                                                      DefaultErrors.FORBIDDEN.getMessage());
        assertEquals("Resource not found",                                                              DefaultErrors.NOT_FOUND.getMessage());
        assertEquals("Request conflicts with actual system state",                                      DefaultErrors.CONFLICT.getMessage());
        assertEquals("Unexpected internal server error (a retry will probably not solve the problem)",  DefaultErrors.UNEXPECTED_SERVER_ERROR.getMessage());
        assertEquals("Transient server error (a retry may solve the problem later)",                    DefaultErrors.TRANSIENT_SERVER_ERROR.getMessage());
    }
}
