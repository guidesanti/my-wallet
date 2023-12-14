package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringToListConverterTest {

    private final StringToStringConverter stringToStringConverter = StringToStringConverter.getInstance();

    private final StringToIntegerConverter stringToIntegerConverter = StringToIntegerConverter.getInstance();

    private final StringToListConverter<String> stringToListOfStringConverter = new StringToListConverter<>(stringToStringConverter);

    private final StringToListConverter<Integer> stringToListOfIntegerConverter = new StringToListConverter<>(stringToIntegerConverter);

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, stringToListOfStringConverter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertThrows(UnsupportedOperationException.class, stringToListOfStringConverter::getTargetType);
    }

    @Test
    public void testConvertListOfStrings() {
        assertEquals(List.of("10", "123", "some-string", "some-other-string"), stringToListOfStringConverter.convert("10,123,some-string,some-other-string"));
        assertEquals(List.of("10", "123", "some-string", "some-other-string"), stringToListOfStringConverter.convert("  10,123,some-string,some-other-string   "));
        assertEquals(List.of("10", " 123", "some-string", "  some-other-string"), stringToListOfStringConverter.convert("  10, 123,some-string,  some-other-string   "));
        assertNull(stringToListOfStringConverter.convert(null));
    }

    @Test
    public void testConvertListOfIntegers() {
        assertEquals(List.of(10, 123), stringToListOfIntegerConverter.convert("10,123"));
        assertEquals(List.of(10, 123, 150), stringToListOfIntegerConverter.convert("  10,123,150   "));
        assertNull(stringToListOfIntegerConverter.convert(null));
    }

    @Test
    public void testReverse() {
        assertThrows(UnsupportedOperationException.class, stringToListOfStringConverter::reverse);
        assertThrows(UnsupportedOperationException.class, stringToListOfIntegerConverter::reverse);
    }
}
