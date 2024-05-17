package br.com.eventhorizon.common.refusal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RefusalReasonCodeTest {

    @Test
    public void testInvalidErrorCodes() {
        // Invalid type
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(null, "DOMAIN", "CODE"));

        // Invalid domain
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, null, "CODE"));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOmAIN", "CODE"));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN-DOMAIN", "CODE"));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN,DOMAIN", "CODE"));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN.DOMAIN", "CODE"));

        // Invalid code
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", null));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", "CoDE"));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", "CODE-CODE"));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", "CODE,CODE"));
        assertThrows(IllegalArgumentException.class, () -> RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", "CODE.CODE"));
    }

    @Test
    public void testValidErrorCodes() {
        var errorCode = RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", "CODE");
        assertEquals(RefusalReasonCode.Type.APP, errorCode.getType());
        assertEquals("DOMAIN", errorCode.getDomain());
        assertEquals("CODE", errorCode.getCode());
        assertEquals("APP.DOMAIN.CODE", errorCode.toString());

        errorCode = RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN_DOMAIN", "CODE_CODE");
        assertEquals(RefusalReasonCode.Type.APP, errorCode.getType());
        assertEquals("DOMAIN_DOMAIN", errorCode.getDomain());
        assertEquals("CODE_CODE", errorCode.getCode());
        assertEquals("APP.DOMAIN_DOMAIN.CODE_CODE", errorCode.toString());
    }

    @Test
    public void testLib() {
        var errorCode = RefusalReasonCode.lib("DOMAIN", "CODE");
        assertEquals(RefusalReasonCode.Type.LIB, errorCode.getType());
        assertEquals("DOMAIN", errorCode.getDomain());
        assertEquals("CODE", errorCode.getCode());
        assertEquals("LIB.DOMAIN.CODE", errorCode.toString());
    }

    @Test
    public void testApp() {
        var errorCode = RefusalReasonCode.app("DOMAIN", "CODE");
        assertEquals(RefusalReasonCode.Type.APP, errorCode.getType());
        assertEquals("DOMAIN", errorCode.getDomain());
        assertEquals("CODE", errorCode.getCode());
        assertEquals("APP.DOMAIN.CODE", errorCode.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Given
        var errorCode1 = RefusalReasonCode.of(RefusalReasonCode.Type.LIB, "DOMAIN", "CODE");
        var errorCode2 = RefusalReasonCode.of(RefusalReasonCode.Type.LIB, "DOMAIN", "CODE");
        var errorCode3 = RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", "CODE");
        var errorCode4 = RefusalReasonCode.of(RefusalReasonCode.Type.APP, "DOMAIN", "CODE");

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
