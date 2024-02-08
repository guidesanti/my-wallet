package br.com.eventhorizon.common.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ErrorCodeTest {

    @Test
    public void testInvalidErrorCodes() {
        // Invalid type
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(null, "DOMAIN", "CODE"));

        // Invalid domain
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, null, "CODE"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOmAIN", "CODE"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN-DOMAIN", "CODE"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN,DOMAIN", "CODE"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN.DOMAIN", "CODE"));

        // Invalid code
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", null));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", "CoDE"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", "CODE-CODE"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", "CODE,CODE"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", "CODE.CODE"));
    }

    @Test
    public void testValidErrorCodes() {
        var errorCode = ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", "CODE");
        assertEquals("APP.DOMAIN.CODE", errorCode.toString());
        errorCode = ErrorCode.of(ErrorCode.Type.APP, "DOMAIN_DOMAIN", "CODE_CODE");
        assertEquals("APP.DOMAIN_DOMAIN.CODE_CODE", errorCode.toString());
    }
}
