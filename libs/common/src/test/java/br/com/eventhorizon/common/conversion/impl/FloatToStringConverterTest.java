package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloatToStringConverterTest {

    private final FloatToStringConverter converter = FloatToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Float.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("-256.45", converter.convert(-256.45f));
        assertEquals("0.0", converter.convert(0.00f));
        assertEquals("1024.0", converter.convert(1024.00f));
        assertEquals("1.4E-45", converter.convert(Float.MIN_VALUE));
        assertEquals("3.4028235E38", converter.convert(Float.MAX_VALUE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Float.class, reverseConverter.getTargetType());
    }
}
