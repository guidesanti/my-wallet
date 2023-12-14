package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToClassConverterTest {

    private final StringToClassConverter converter = StringToClassConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Class.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals(Boolean.class, converter.convert("java.lang.Boolean"));
        assertEquals(StringToClassConverter.class, converter.convert("br.com.eventhorizon.common.conversion.impl.StringToClassConverter"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-class-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Class.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
