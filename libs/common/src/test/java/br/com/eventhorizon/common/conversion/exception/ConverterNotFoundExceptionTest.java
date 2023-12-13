package br.com.eventhorizon.common.conversion.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterNotFoundExceptionTest {

    @Test
    public void test() {
        var exception = new ConverterNotFoundException(Object.class, Object.class);
        assertEquals(Object.class, exception.getSourceType());
        assertEquals(Object.class, exception.getTargetType());
        assertEquals("Converter of source type 'class java.lang.Object' and target type 'class java.lang.Object' not found", exception.getMessage());
    }
}
