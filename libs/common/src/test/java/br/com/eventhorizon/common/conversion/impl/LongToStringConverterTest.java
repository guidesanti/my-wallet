package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongToStringConverterTest {

    private final LongToStringConverter converter = LongToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Long.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("-256", converter.convert(-256L));
        assertEquals("0", converter.convert(0L));
        assertEquals("1024", converter.convert(1024L));
        assertEquals("-9223372036854775808", converter.convert(Long.MIN_VALUE));
        assertEquals("9223372036854775807", converter.convert(Long.MAX_VALUE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Long.class, reverseConverter.getTargetType());
    }
}
