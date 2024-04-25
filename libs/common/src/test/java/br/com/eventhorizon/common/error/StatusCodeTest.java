package br.com.eventhorizon.common.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusCodeTest {

    @Test
    void testOf() {
        assertThrows(IllegalArgumentException.class, () -> StatusCode.of(null));
        assertEquals(StatusCode.SUCCESS, StatusCode.of("SUCCESS"));
        assertEquals(StatusCode.ERROR, StatusCode.of("ERROR"));
        assertThrows(IllegalArgumentException.class, () -> StatusCode.of("INVALID"));

        assertThrows(IllegalArgumentException.class, () -> StatusCode.of(null, true));
        assertEquals(StatusCode.SUCCESS, StatusCode.of("SUCCESS", true));
        assertEquals(StatusCode.ERROR, StatusCode.of("ERROR", true));
        assertThrows(IllegalArgumentException.class, () -> StatusCode.of("INVALID", true));

        assertEquals(StatusCode.UNKNOWN, StatusCode.of(null, false));
        assertEquals(StatusCode.SUCCESS, StatusCode.of("SUCCESS", false));
        assertEquals(StatusCode.ERROR, StatusCode.of("ERROR", false));
        assertEquals(StatusCode.UNKNOWN, StatusCode.of("INVALID", false));
    }
}
