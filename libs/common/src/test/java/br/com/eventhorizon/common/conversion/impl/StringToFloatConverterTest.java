package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToFloatConverterTest {

    private final StringToFloatConverter converter = StringToFloatConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Float.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals(-25.234f, converter.convert("-25.234"));
        assertEquals(0.0f, converter.convert("0.0"));
        assertEquals(0.0f, converter.convert("0"));
        assertEquals(1024.0f, converter.convert("1024"));
        assertEquals(1024.109f, converter.convert("1024.109"));
        assertEquals(Float.MIN_VALUE, converter.convert("1.4E-45"));
        assertEquals(Float.MAX_VALUE, converter.convert("3.4028235E38"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-float-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Float.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
