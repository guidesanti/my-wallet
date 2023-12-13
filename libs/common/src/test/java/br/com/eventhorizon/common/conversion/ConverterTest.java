package br.com.eventhorizon.common.conversion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConverterTest {

    @Test
    public void testReverse() {
        var converter = new Converter<>() {
            @Override
            public Class<Object> getSourceType() {
                return null;
            }

            @Override
            public Class<Object> getTargetType() {
                return null;
            }

            @Override
            public Object convert(Object value) {
                return null;
            }
        };

        assertThrows(UnsupportedOperationException.class, converter::reverse);
    }
}
