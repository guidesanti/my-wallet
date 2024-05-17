package br.com.eventhorizon.messaging.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class HeaderTest {

    @Test
    public void testGetName() {
        // This is to ensure we shouldn't change header name, otherwise existing code could be broken
        assertEquals("EH-Created-At",           Header.CREATED_AT.getName());
        assertEquals("EH-Published-At",         Header.PUBLISHED_AT.getName());
        assertEquals("EH-Idempotence-ID",       Header.IDEMPOTENCE_ID.getName());
        assertEquals("EH-Trace-ID",             Header.TRACE_ID.getName());
        assertEquals("EH-Publisher",            Header.PUBLISHER.getName());
        assertEquals("EH-Retry-Count",          Header.RETRY_COUNT.getName());
        assertEquals("EH-Error-Message",        Header.ERROR_MESSAGE.getName());
        assertEquals("EH-Error-Stack-Trace",    Header.ERROR_STACK_TRACE.getName());
    }

    @Test
    public void testGetDescription() {
        // This is to remember we should add description always
        assertFalse(StringUtils.isBlank(Header.CREATED_AT.getDescription()));
        assertFalse(StringUtils.isBlank(Header.PUBLISHED_AT.getDescription()));
        assertFalse(StringUtils.isBlank(Header.IDEMPOTENCE_ID.getDescription()));
        assertFalse(StringUtils.isBlank(Header.TRACE_ID.getDescription()));
        assertFalse(StringUtils.isBlank(Header.PUBLISHER.getDescription()));
        assertFalse(StringUtils.isBlank(Header.RETRY_COUNT.getDescription()));
        assertFalse(StringUtils.isBlank(Header.ERROR_MESSAGE.getDescription()));
        assertFalse(StringUtils.isBlank(Header.ERROR_STACK_TRACE.getDescription()));
    }
}
