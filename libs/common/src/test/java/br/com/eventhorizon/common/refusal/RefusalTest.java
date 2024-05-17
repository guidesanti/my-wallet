package br.com.eventhorizon.common.refusal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefusalTest {

    @Test
    void testOfWithInvalidArguments() {
        var errorCode = RefusalReasonCode.lib("DOMAIN", "CODE");
        assertThrows(NullPointerException.class, () -> Refusal.of(null, "message"));
        assertThrows(NullPointerException.class, () -> Refusal.of(errorCode, null));
        assertThrows(NullPointerException.class, () -> Refusal.of(null, "message", "additional information"));
        assertThrows(NullPointerException.class, () -> Refusal.of(errorCode, null, "additional information"));
    }

    @Test
    void testOf() {
        // Given
        RefusalReasonCode code = RefusalReasonCode.lib("DOMAIN", "CODE");
        String message = "Test message";

        // When
        Refusal error = Refusal.of(code, message);

        // Then
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
        assertTrue(error.getAdditionalInformation().isEmpty());
   }

    @Test
    void testOfWithAdditionalInformation() {
        // Given
        RefusalReasonCode code = RefusalReasonCode.lib("DOMAIN", "CODE");
        String message = "Test message";
        String additionalInformation = "Additional information";

        // When
        Refusal error = Refusal.of(code, message, additionalInformation);

        // Then
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
        assertTrue(error.getAdditionalInformation().isPresent());
        assertEquals(additionalInformation, error.getAdditionalInformation().get());
    }

    @Test
    void testToString() {
        // Given
        RefusalReasonCode code = RefusalReasonCode.lib("DOMAIN", "CODE");
        String message = "Test message";
        String additionalInformation = "Additional information";

        // When
        var result = Refusal.of(code, message, additionalInformation).toString();

        // Then
        assertEquals("Refusal(code=LIB.DOMAIN.CODE, message=Test message, additionalInformation=Optional[Additional information])", result);
    }
}
