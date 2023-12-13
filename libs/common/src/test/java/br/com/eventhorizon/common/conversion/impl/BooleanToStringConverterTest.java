package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.Converter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanToStringConverterTest {

    private static final String TRUE = "TRUE";

    private static final String FALSE = "FALSE";

    private final BooleanToStringConverter converter = BooleanToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Boolean.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals(TRUE, converter.convert(true));
        assertEquals(TRUE, converter.convert(Boolean.TRUE));
        assertEquals(FALSE, converter.convert(false));
        assertEquals(FALSE, converter.convert(Boolean.FALSE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Boolean.class, reverseConverter.getTargetType());
    }
}
