package br.com.eventhorizon.common.messaging.impl;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.common.messaging.Message;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testNull() {
        assertThrows(NullPointerException.class, () -> Message.builder().headers(Headers.emptyHeaders()).content(null).build());
    }

    @Test
    public void testEmptyHeaders() {
        var content = new Object();
        var message = Message.builder().content(content).build();
        assertNotNull(message.headers());
        assertEquals(Headers.emptyHeaders(), message.headers());
        assertTrue(message.headers().isEmpty());
        assertNotNull(message.content());
        assertSame(content, message.content());
    }

    @Test
    public void testNonNull() {
        Headers headers = Headers.builder()
                .headers(Map.of(
                        "header1", List.of("header1-value1", "header1-value1"),
                        "header2", List.of("header2-value1", "header2-value1")))
                .build();
        Object body = new Object();
        Message<Object> message = Message.builder()
                .headers(headers)
                .content(body)
                .build();
        assertNotNull(message.headers());
        assertEquals(headers, message.headers());
        assertFalse(message.headers().isEmpty());
        assertNotNull(message.content());
    }

}
