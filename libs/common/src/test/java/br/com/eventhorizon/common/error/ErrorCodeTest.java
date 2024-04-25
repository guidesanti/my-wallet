package br.com.eventhorizon.common.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(ErrorCode.Type.APP, errorCode.getType());
        assertEquals("DOMAIN", errorCode.getDomain());
        assertEquals("CODE", errorCode.getCode());
        assertEquals("APP.DOMAIN.CODE", errorCode.toString());

        errorCode = ErrorCode.of(ErrorCode.Type.APP, "DOMAIN_DOMAIN", "CODE_CODE");
        assertEquals(ErrorCode.Type.APP, errorCode.getType());
        assertEquals("DOMAIN_DOMAIN", errorCode.getDomain());
        assertEquals("CODE_CODE", errorCode.getCode());
        assertEquals("APP.DOMAIN_DOMAIN.CODE_CODE", errorCode.toString());
    }

    @Test
    public void testLib() {
        var errorCode = ErrorCode.lib("DOMAIN", "CODE");
        assertEquals(ErrorCode.Type.LIB, errorCode.getType());
        assertEquals("DOMAIN", errorCode.getDomain());
        assertEquals("CODE", errorCode.getCode());
        assertEquals("LIB.DOMAIN.CODE", errorCode.toString());
    }

    @Test
    public void testApp() {
        var errorCode = ErrorCode.app("DOMAIN", "CODE");
        assertEquals(ErrorCode.Type.APP, errorCode.getType());
        assertEquals("DOMAIN", errorCode.getDomain());
        assertEquals("CODE", errorCode.getCode());
        assertEquals("APP.DOMAIN.CODE", errorCode.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Given
        var errorCode1 = ErrorCode.of(ErrorCode.Type.LIB, "DOMAIN", "CODE");
        var errorCode2 = ErrorCode.of(ErrorCode.Type.LIB, "DOMAIN", "CODE");
        var errorCode3 = ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", "CODE");
        var errorCode4 = ErrorCode.of(ErrorCode.Type.APP, "DOMAIN", "CODE");

        // Then
        assertNotEquals(errorCode1, null);
        assertNotEquals(errorCode2, null);
        assertNotEquals(errorCode3, null);
        assertNotEquals(errorCode4, null);

        assertEquals(errorCode1, errorCode2);
        assertNotEquals(errorCode1, errorCode3);
        assertNotEquals(errorCode1, errorCode4);
        assertNotEquals(errorCode2, errorCode3);
        assertNotEquals(errorCode2, errorCode4);
        assertEquals(errorCode3, errorCode4);

        assertEquals(errorCode1.hashCode(), errorCode2.hashCode());
        assertNotEquals(errorCode1.hashCode(), errorCode3.hashCode());
        assertNotEquals(errorCode1.hashCode(), errorCode4.hashCode());
        assertNotEquals(errorCode2.hashCode(), errorCode3.hashCode());
        assertNotEquals(errorCode2.hashCode(), errorCode4.hashCode());
        assertEquals(errorCode3.hashCode(), errorCode4.hashCode());
    }
}
