package br.com.eventhorizon.common.conversion.impl;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

public class OffsetDateTimeToStringConverterTest {

    private final OffsetDateTimeToStringConverter converter = OffsetDateTimeToStringConverter.getInstance();

    @Test
    public void testGetSourceType() {
        assertEquals(OffsetDateTime.class, converter.getSourceType());
    }

    @Test
    public void testGetTargetType() {
        assertEquals(String.class, converter.getTargetType());
    }

    @Test
    public void testConvert() {
        assertEquals("2023-12-13T14:07:07.891042851-03:00", converter.convert(OffsetDateTime.of(2023, 12, 13, 14, 7, 7, 891042851, ZoneOffset.of("-3"))));
        assertEquals("2023-12-13T14:07:07.891042851Z", converter.convert(OffsetDateTime.of(2023, 12, 13, 14, 7, 7, 891042851, ZoneOffset.of("Z"))));
        assertEquals("2023-12-13T14:07:07.891042851Z", converter.convert(OffsetDateTime.of(2023, 12, 13, 14, 7, 7, 891042851, ZoneOffset.UTC)));
        assertNull(converter.convert(null));
    }

    @Test
    public void testReverse() {
        var reverseConverter = converter.reverse();
        assertNotNull(reverseConverter);
        assertEquals(String.class, reverseConverter.getSourceType());
        assertEquals(OffsetDateTime.class, reverseConverter.getTargetType());
    }
}
