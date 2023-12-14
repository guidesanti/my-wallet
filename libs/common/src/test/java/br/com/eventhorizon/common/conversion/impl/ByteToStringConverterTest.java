package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ByteToStringConverterTest {

    private final ByteToStringConverter converter = ByteToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Byte.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("-120", converter.convert((byte) -120));
        assertEquals("0", converter.convert((byte) 0));
        assertEquals("25", converter.convert((byte) 25));
        assertEquals("-128", converter.convert(Byte.MIN_VALUE));
        assertEquals("127", converter.convert(Byte.MAX_VALUE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Byte.class, reverseConverter.getTargetType());
    }
}
