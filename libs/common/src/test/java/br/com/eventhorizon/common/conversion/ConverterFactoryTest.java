package br.com.eventhorizon.common.conversion;

import br.com.eventhorizon.common.conversion.exception.ConverterNotFoundException;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterFactoryTest {

    private static final List<Class<?>[]> types = new ArrayList<>();

    static {
        types.add(new Class[] { Boolean.class, String.class });
        types.add(new Class[] { Character.class, String.class });
        types.add(new Class[] { Byte.class, String.class });
        types.add(new Class[] { Short.class, String.class });
        types.add(new Class[] { Integer.class, String.class });
        types.add(new Class[] { Long.class, String.class });
        types.add(new Class[] { Float.class, String.class });
        types.add(new Class[] { Double.class, String.class });
        types.add(new Class[] { String.class, String.class });
        types.add(new Class[] { OffsetDateTime.class, String.class });
        types.add(new Class[] { Class.class, String.class });
    }

    @Test
    public void testGetConverter() {
        types.forEach(classes -> {
            var type1 = classes[0];
            var type2 = classes[1];

            // Converter
            Converter<?, ?> converter = ConverterFactory.getConverter(type1, type2);
            assertNotNull(converter);
            assertEquals(type1, converter.getSourceType());
            assertEquals(type2, converter.getTargetType());

            // Reverse converter
            converter = ConverterFactory.getConverter(type2, type1);
            assertNotNull(converter);
            assertEquals(type2, converter.getSourceType());
            assertEquals(type1, converter.getTargetType());
        });
    }

    @Test
    public void testGetConverterNonExistingConverter() {
        assertThrows(ConverterNotFoundException.class, () -> ConverterFactory.getConverter(SomeClassA.class, SomeClassB.class));
    }

    private static class SomeClassA { }

    private static class SomeClassB { }
}
