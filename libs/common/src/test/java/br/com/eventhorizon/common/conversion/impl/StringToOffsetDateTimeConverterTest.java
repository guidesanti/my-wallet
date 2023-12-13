package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

public class StringToOffsetDateTimeConverterTest {

    private final StringToOffsetDateTimeConverter converter = StringToOffsetDateTimeConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(String.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(OffsetDateTime.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals(OffsetDateTime.of(2023, 12, 13, 14, 7, 7, 891042851, ZoneOffset.of("-3")), converter.convert("2023-12-13T14:07:07.891042851-03:00"));
        assertEquals(OffsetDateTime.of(2023, 12, 13, 14, 7, 7, 891042851, ZoneOffset.of("Z")), converter.convert("2023-12-13T14:07:07.891042851Z"));
        assertEquals(OffsetDateTime.of(2023, 12, 13, 14, 7, 7, 891042851, ZoneOffset.UTC), converter.convert("2023-12-13T14:07:07.891042851Z"));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(OffsetDateTime.class, reverseConverter.getSourceType());
        assertEquals(String.class, reverseConverter.getTargetType());
    }
}
