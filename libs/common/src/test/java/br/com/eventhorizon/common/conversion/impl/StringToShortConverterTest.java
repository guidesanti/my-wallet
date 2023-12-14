package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToShortConverterTest {

    private final StringToShortConverter converter = StringToShortConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Short.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals((short) -120, converter.convert("-120"));
        assertEquals((short) 0, converter.convert("0"));
        assertEquals((short) 25, converter.convert("25"));
        assertEquals(Short.MIN_VALUE, converter.convert("-32768"));
        assertEquals(Short.MAX_VALUE, converter.convert("32767"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-short-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Short.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
