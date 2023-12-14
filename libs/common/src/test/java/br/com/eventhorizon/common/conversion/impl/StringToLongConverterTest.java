package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToLongConverterTest {

    private final StringToLongConverter converter = StringToLongConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Long.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals(-256L, converter.convert("-256"));
        assertEquals(0L, converter.convert("0"));
        assertEquals(1024L, converter.convert("1024"));
        assertEquals(Long.MIN_VALUE, converter.convert("-9223372036854775808"));
        assertEquals(Long.MAX_VALUE, converter.convert("9223372036854775807"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-long-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Long.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
