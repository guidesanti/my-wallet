package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegerToStringConverterTest {

    private final IntegerToStringConverter converter = IntegerToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Integer.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("-256", converter.convert(-256));
        assertEquals("0", converter.convert(0));
        assertEquals("1024", converter.convert(1024));
        assertEquals("-2147483648", converter.convert(Integer.MIN_VALUE));
        assertEquals("2147483647", converter.convert(Integer.MAX_VALUE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Integer.class, reverseConverter.getTargetType());
    }
}
