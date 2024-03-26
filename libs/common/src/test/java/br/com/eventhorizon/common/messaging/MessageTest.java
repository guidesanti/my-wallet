package br.com.eventhorizon.common.messaging;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testBuildWithInvalidHeaders() {
        assertThrows(NullPointerException.class, () -> Message.builder().header(null, "value").build());
        assertThrows(NullPointerException.class, () -> Message.builder().header("name", (String) null).build());
        assertThrows(NullPointerException.class, () -> Message.builder().header(null, Collections.emptyList()).build());
        assertThrows(NullPointerException.class, () -> Message.builder().header("name", (List<String>) null).build());
    }

    @Test
    public void testBuildWithInvalidContent() {
        assertThrows(NullPointerException.class, () -> Message.builder().content(null).build());
    }

    @Test
    public void testBuildWithEmptyHeaders() {
        // Given
        var content = new Object();
        var message = Message.builder().content(content).build();

        // Then
        assertNotNull(message.headers());
        assertTrue(message.headers().isEmpty());
        assertNotNull(message.content());
        assertSame(content, message.content());
    }

    @Test
    public void testNonNull() {
        // Given
        Object content = new Object();

        // When
        Message<Object> message = Message.<Object>builder()
                .header("header1", List.of("header1-value1", "header1-value1"))
                .header("header2", List.of("header2-value1", "header2-value1"))
                .content(content)
                .build();

        // Then
        assertNotNull(message.headers());
        assertFalse(message.headers().isEmpty());
        assertTrue(message.headers().contains("header1"));
        assertEquals(List.of("header1-value1", "header1-value1"), message.headers().values("header1"));
        assertTrue(message.headers().contains("header2"));
        assertEquals(List.of("header2-value1", "header2-value1"), message.headers().values("header2"));
        assertNotNull(message.content());
        assertSame(content, message.content());
    }
}
