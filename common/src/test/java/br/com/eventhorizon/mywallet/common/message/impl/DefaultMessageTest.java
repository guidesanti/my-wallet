package br.com.eventhorizon.mywallet.common.message.impl;

import br.com.eventhorizon.mywallet.common.message.Headers;
import br.com.eventhorizon.mywallet.common.message.Message;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMessageTest {

    @Test
    public void testNull() {
        Message<Object> message = DefaultMessage.builder()
                .build();
        assertNotNull(message.headers());
        assertSame(DefaultHeaders.EMPTY_HEADERS, message.headers());
        assertTrue(message.headers().isEmpty());
        assertNotNull(message.content());

        message = DefaultMessage.builder()
                .headers(null)
                .content(null)
                .build();
        assertNotNull(message.headers());
        assertSame(DefaultHeaders.EMPTY_HEADERS, message.headers());
        assertTrue(message.headers().isEmpty());
        assertNotNull(message.content());
    }

    @Test
    public void testNonNull() {
        Headers headers = DefaultHeaders.builder()
                .headers(Map.of(
                        "header1", List.of("header1-value1", "header1-value1"),
                        "header2", List.of("header2-value1", "header2-value1")))
                .build();
        Object body = new Object();
        Message<Object> message = DefaultMessage.builder()
                .headers(headers)
                .content(body)
                .build();
        assertNotNull(message.headers());
        assertSame(headers, message.headers());
        assertFalse(message.headers().isEmpty());
        assertNotNull(message.content());
    }

}
