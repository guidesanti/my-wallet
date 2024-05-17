package br.com.eventhorizon.common.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusCodeTest {

    @Test
    void testOf() {
        assertEquals(StatusCode.UNKNOWN, StatusCode.of(null));
        assertEquals(StatusCode.UNKNOWN, StatusCode.of("INVALID"));
        assertEquals(StatusCode.UNKNOWN, StatusCode.of("UNKNOWN"));
        assertEquals(StatusCode.SUCCESS, StatusCode.of("SUCCESS"));
        assertEquals(StatusCode.REFUSED, StatusCode.of("REFUSED"));
        assertEquals(StatusCode.FAILURE, StatusCode.of("FAILURE"));
    }

    @Test
    void testOfOrThrow() {
        assertThrows(IllegalArgumentException.class, () -> StatusCode.ofOrThrow(null));
        assertThrows(IllegalArgumentException.class, () -> StatusCode.ofOrThrow("INVALID"));
        assertEquals(StatusCode.UNKNOWN, StatusCode.ofOrThrow("UNKNOWN"));
        assertEquals(StatusCode.SUCCESS, StatusCode.ofOrThrow("SUCCESS"));
        assertEquals(StatusCode.REFUSED, StatusCode.ofOrThrow("REFUSED"));
        assertEquals(StatusCode.FAILURE, StatusCode.ofOrThrow("FAILURE"));
    }

    @Test
    void testValues() {
        assertEquals(4, StatusCode.values().length);
        assertArrayEquals(
                new StatusCode[] { StatusCode.UNKNOWN, StatusCode.SUCCESS, StatusCode.REFUSED, StatusCode.FAILURE },
                StatusCode.values());
    }
}
