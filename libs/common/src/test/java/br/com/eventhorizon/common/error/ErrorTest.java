
package br.com.eventhorizon.common.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorTest {

    @Test
    void testOfWithInvalidArguments() {
        var errorCode = ErrorCode.lib("DOMAIN", "CODE");
        assertThrows(NullPointerException.class, () -> Error.of(null, "message"));
        assertThrows(NullPointerException.class, () -> Error.of(errorCode, null));
        assertThrows(NullPointerException.class, () -> Error.of(null, "message", "additional information"));
        assertThrows(NullPointerException.class, () -> Error.of(errorCode, null, "additional information"));
    }

    @Test
    void testOfWithTwoParameters() {
        // Given
        ErrorCode code = ErrorCode.lib("DOMAIN", "CODE");
        String message = "Test message";

        // When
        Error error = Error.of(code, message);

        // Then
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
        assertTrue(error.getAdditionalInformation().isEmpty());
   }

    @Test
    void testOfWithThreeParameters() {
        // Given
        ErrorCode code = ErrorCode.lib("DOMAIN", "CODE");
        String message = "Test message";
        String additionalInformation = "Additional information";

        // When
        Error error = Error.of(code, message, additionalInformation);

        // Then
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
        assertTrue(error.getAdditionalInformation().isPresent());
        assertEquals(additionalInformation, error.getAdditionalInformation().get());
    }

    @Test
    void testToString() {
        // Given
        ErrorCode code = ErrorCode.lib("DOMAIN", "CODE");
        String message = "Test message";
        String additionalInformation = "Additional information";

        // When
        var result = Error.of(code, message, additionalInformation).toString();

        // Then
        assertEquals("Error(code=LIB.DOMAIN.CODE, message=Test message, additionalInformation=Optional[Additional information])", result);
    }
}
