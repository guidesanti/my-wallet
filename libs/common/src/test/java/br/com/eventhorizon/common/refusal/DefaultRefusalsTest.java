package br.com.eventhorizon.common.refusal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultRefusalsTest {

    @Test
    public void testGetCode() {
        assertEquals(RefusalReasonCode.lib("COMMON", "BUSINESS_RULE_NOT_MET"),   DefaultRefusals.BUSINESS_RULE_NOT_MET.getCode());
        assertEquals(RefusalReasonCode.lib("COMMON", "BAD_REQUEST"),             DefaultRefusals.BAD_REQUEST.getCode());
        assertEquals(RefusalReasonCode.lib("COMMON", "UNAUTHORIZED"),            DefaultRefusals.UNAUTHORIZED.getCode());
        assertEquals(RefusalReasonCode.lib("COMMON", "FORBIDDEN"),               DefaultRefusals.FORBIDDEN.getCode());
        assertEquals(RefusalReasonCode.lib("COMMON", "NOT_FOUND"),               DefaultRefusals.NOT_FOUND.getCode());
        assertEquals(RefusalReasonCode.lib("COMMON", "CONFLICT"),                DefaultRefusals.CONFLICT.getCode());
        assertEquals(RefusalReasonCode.lib("COMMON", "UNEXPECTED_SERVER_ERROR"), DefaultRefusals.UNEXPECTED_SERVER_ERROR.getCode());
        assertEquals(RefusalReasonCode.lib("COMMON", "TRANSIENT_SERVER_ERROR"),  DefaultRefusals.TRANSIENT_SERVER_ERROR.getCode());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Business rule not met",                                                           DefaultRefusals.BUSINESS_RULE_NOT_MET.getMessage());
        assertEquals("Invalid request content format",                                                  DefaultRefusals.BAD_REQUEST.getMessage());
        assertEquals("Request not authorized",                                                          DefaultRefusals.UNAUTHORIZED.getMessage());
        assertEquals("Insufficient access rights",                                                      DefaultRefusals.FORBIDDEN.getMessage());
        assertEquals("Resource not found",                                                              DefaultRefusals.NOT_FOUND.getMessage());
        assertEquals("Request conflicts with actual system state",                                      DefaultRefusals.CONFLICT.getMessage());
        assertEquals("Unexpected internal server error (a retry will probably not solve the problem)",  DefaultRefusals.UNEXPECTED_SERVER_ERROR.getMessage());
        assertEquals("Transient server error (a retry may solve the problem later)",                    DefaultRefusals.TRANSIENT_SERVER_ERROR.getMessage());
    }
}
