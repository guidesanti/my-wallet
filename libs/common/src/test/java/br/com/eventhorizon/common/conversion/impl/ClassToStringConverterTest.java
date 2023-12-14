package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassToStringConverterTest {

    private final ClassToStringConverter converter = ClassToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(Class.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("java.lang.Boolean", converter.convert(Boolean.class));
        assertEquals("br.com.eventhorizon.common.conversion.impl.ClassToStringConverter", converter.convert(ClassToStringConverter.class));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(Class.class, reverseConverter.getTargetType());
    }
}
