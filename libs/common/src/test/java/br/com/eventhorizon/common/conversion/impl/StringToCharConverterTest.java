package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToCharConverterTest {

    private final StringToCharConverter converter = StringToCharConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Character.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals('1', converter.convert("1"));
        assertEquals('A', converter.convert("A"));
        assertEquals('c', converter.convert("c"));
        assertEquals(Character.MIN_VALUE, converter.convert("\u0000"));
        assertEquals(Character.MAX_VALUE, converter.convert("\uFFFF"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-character-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Character.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
