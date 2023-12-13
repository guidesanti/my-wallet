package br.com.eventhorizon.common.conversion.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversionExceptionTest {

    @Test
    public void test() {
        var exception = new ConversionException("some-string-value", String.class, Object.class);
        assertEquals("some-string-value", exception.getSourceValue());
        assertEquals(String.class, exception.getSourceType());
        assertEquals(Object.class, exception.getTargetType());
        assertEquals("Cannot convert value 'some-string-value' of type 'class java.lang.String' to type 'class java.lang.Object'", exception.getMessage());

        exception = new ConversionException("some-string-value", String.class, Object.class, new RuntimeException("Some other exception"));
        assertEquals("some-string-value", exception.getSourceValue());
        assertEquals(String.class, exception.getSourceType());
        assertEquals(Object.class, exception.getTargetType());
        assertEquals("Cannot convert value 'some-string-value' of type 'class java.lang.String' to type 'class java.lang.Object'", exception.getMessage());
    }
}
