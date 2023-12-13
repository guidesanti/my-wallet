package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToBooleanConverterTest {

    private final StringToBooleanConverter converter = StringToBooleanConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Boolean.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertTrue(converter.convert("true"));
        assertTrue(converter.convert("True"));
        assertTrue(converter.convert("TrUe"));
        assertTrue(converter.convert("TRUE"));
        assertFalse(converter.convert("false"));
        assertFalse(converter.convert("False"));
        assertFalse(converter.convert("FaLse"));
        assertFalse(converter.convert("FALSE"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-boolean-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Boolean.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
