package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharToStringConverterTest {

    private final CharToStringConverter converter = CharToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Character.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("1", converter.convert('1'));
        assertEquals("A", converter.convert('A'));
        assertEquals("c", converter.convert('c'));
        assertEquals("\u0000", converter.convert(Character.MIN_VALUE));
        assertEquals("\uFFFF", converter.convert(Character.MAX_VALUE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Character.class, reverseConverter.getTargetType());
    }
}
