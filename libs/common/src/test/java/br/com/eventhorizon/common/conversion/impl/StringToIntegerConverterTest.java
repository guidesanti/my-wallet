package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToIntegerConverterTest {

    private final StringToIntegerConverter converter = StringToIntegerConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Integer.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals(-256, converter.convert("-256"));
        assertEquals(0, converter.convert("0"));
        assertEquals(1024, converter.convert("1024"));
        assertEquals(Integer.MIN_VALUE, converter.convert("-2147483648"));
        assertEquals(Integer.MAX_VALUE, converter.convert("2147483647"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-integer-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Integer.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
