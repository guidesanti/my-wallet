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
        assertTrue(converter.convert("yes"));
        assertTrue(converter.convert("Yes"));
        assertTrue(converter.convert("YeS"));
        assertTrue(converter.convert("YES"));
        assertTrue(converter.convert("Y"));
        assertTrue(converter.convert("y"));
        assertTrue(converter.convert("on"));
        assertTrue(converter.convert("On"));
        assertTrue(converter.convert("oN"));
        assertTrue(converter.convert("ON"));
        assertTrue(converter.convert("1"));

        assertFalse(converter.convert("false"));
        assertFalse(converter.convert("False"));
        assertFalse(converter.convert("FaLse"));
        assertFalse(converter.convert("FALSE"));
        assertFalse(converter.convert("no"));
        assertFalse(converter.convert("No"));
        assertFalse(converter.convert("nO"));
        assertFalse(converter.convert("NO"));
        assertFalse(converter.convert("n"));
        assertFalse(converter.convert("N"));
        assertFalse(converter.convert("off"));
        assertFalse(converter.convert("Off"));
        assertFalse(converter.convert("OfF"));
        assertFalse(converter.convert("OFF"));
        assertFalse(converter.convert("0"));

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
