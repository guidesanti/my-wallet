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
        assertEquals("eh-created-at",           Header.CREATED_AT.getName());
        assertEquals("eh-published-at",         Header.PUBLISHED_AT.getName());
        assertEquals("eh-trace-id",             Header.TRACE_ID.getName());
        assertEquals("eh-publisher",            Header.PUBLISHER.getName());
        assertEquals("eh-retry-count",          Header.RETRY_COUNT.getName());
        assertEquals("eh-error-category",       Header.ERROR_CATEGORY.getName());
        assertEquals("eh-error-code",           Header.ERROR_CODE.getName());
        assertEquals("eh-error-message",        Header.ERROR_MESSAGE.getName());
        assertEquals("eh-error-stack-trace",    Header.ERROR_STACK_TRACE.getName());
    }

    @Test
    public void testGetDescription() {
        // This is to remember we should add description always
        assertFalse(StringUtils.isBlank(Header.CREATED_AT.getDescription()));
        assertFalse(StringUtils.isBlank(Header.PUBLISHED_AT.getDescription()));
        assertFalse(StringUtils.isBlank(Header.TRACE_ID.getDescription()));
        assertFalse(StringUtils.isBlank(Header.PUBLISHER.getDescription()));
        assertFalse(StringUtils.isBlank(Header.RETRY_COUNT.getDescription()));
        assertFalse(StringUtils.isBlank(Header.ERROR_CATEGORY.getDescription()));
        assertFalse(StringUtils.isBlank(Header.ERROR_CODE.getDescription()));
        assertFalse(StringUtils.isBlank(Header.ERROR_MESSAGE.getDescription()));
        assertFalse(StringUtils.isBlank(Header.ERROR_STACK_TRACE.getDescription()));
    }
}
