package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.exception.ConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringToByteConverterTest {

    private final StringToByteConverter converter = StringToByteConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(Byte.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals((byte) -120, converter.convert("-120"));
        assertEquals((byte) 0, converter.convert("0"));
        assertEquals((byte) 25, converter.convert("25"));
        assertEquals(Byte.MIN_VALUE, converter.convert("-128"));
        assertEquals(Byte.MAX_VALUE, converter.convert("127"));
        assertNull(converter.convert(null));
        assertThrows(ConversionException.class, () -> converter.convert(""));
        assertThrows(ConversionException.class, () -> converter.convert("invalid-byte-value"));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(Byte.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
