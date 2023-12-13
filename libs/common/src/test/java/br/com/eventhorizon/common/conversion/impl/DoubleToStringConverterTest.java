package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleToStringConverterTest {

    private final DoubleToStringConverter converter = DoubleToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Double.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("-256.45", converter.convert(-256.45));
        assertEquals("0.0", converter.convert(0.00));
        assertEquals("1024.0", converter.convert(1024.00));
        assertEquals("4.9E-324", converter.convert(Double.MIN_VALUE));
        assertEquals("1.7976931348623157E308", converter.convert(Double.MAX_VALUE));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Double.class, reverseConverter.getTargetType());
    }
}
