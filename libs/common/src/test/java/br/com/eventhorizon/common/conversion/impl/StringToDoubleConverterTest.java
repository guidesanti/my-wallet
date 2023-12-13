package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToDoubleConverterTest {

    private final StringToDoubleConverter converter = StringToDoubleConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Double.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals(-25.234, converter.convert("-25.234"));
        assertEquals(0.0, converter.convert("0.0"));
        assertEquals(0.0, converter.convert("0"));
        assertEquals(1024.0, converter.convert("1024"));
        assertEquals(1024.109, converter.convert("1024.109"));
        assertEquals(Double.MIN_VALUE, converter.convert("4.9E-324"));
        assertEquals(Double.MAX_VALUE, converter.convert("1.7976931348623157E308"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-double-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Double.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
