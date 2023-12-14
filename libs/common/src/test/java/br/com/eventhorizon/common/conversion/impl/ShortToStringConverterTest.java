package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShortToStringConverterTest {

    private final ShortToStringConverter converter = ShortToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Short.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("-120", converter.convert((short) -120));
        assertEquals("0", converter.convert((short) 0));
        assertEquals("25", converter.convert((short) 25));
        assertEquals("-32768", converter.convert(Short.MIN_VALUE));
        assertEquals("32767", converter.convert(Short.MAX_VALUE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Short.class, reverseConverter.getTargetType());
    }
}
