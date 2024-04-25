package br.com.eventhorizon.common.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ErrorCategoryTest {

    @Test
    void testOf() {
        assertThrows(IllegalArgumentException.class, () -> ErrorCategory.of(null));
        assertEquals(ErrorCategory.BUSINESS_ERROR, ErrorCategory.of("BUSINESS_ERROR"));
        assertEquals(ErrorCategory.CLIENT_ERROR, ErrorCategory.of("CLIENT_ERROR"));
        assertEquals(ErrorCategory.SERVER_ERROR, ErrorCategory.of("SERVER_ERROR"));
        assertThrows(IllegalArgumentException.class, () -> ErrorCategory.of("INVALID"));

        assertThrows(IllegalArgumentException.class, () -> ErrorCategory.of(null, true));
        assertEquals(ErrorCategory.BUSINESS_ERROR, ErrorCategory.of("BUSINESS_ERROR", true));
        assertEquals(ErrorCategory.CLIENT_ERROR, ErrorCategory.of("CLIENT_ERROR", true));
        assertEquals(ErrorCategory.SERVER_ERROR, ErrorCategory.of("SERVER_ERROR", true));
        assertThrows(IllegalArgumentException.class, () -> ErrorCategory.of("INVALID", true));

        assertEquals(ErrorCategory.UNKNOWN, ErrorCategory.of(null, false));
        assertEquals(ErrorCategory.BUSINESS_ERROR, ErrorCategory.of("BUSINESS_ERROR", false));
        assertEquals(ErrorCategory.CLIENT_ERROR, ErrorCategory.of("CLIENT_ERROR", false));
        assertEquals(ErrorCategory.SERVER_ERROR, ErrorCategory.of("SERVER_ERROR", false));
        assertEquals(ErrorCategory.UNKNOWN, ErrorCategory.of("INVALID", false));
    }
}
