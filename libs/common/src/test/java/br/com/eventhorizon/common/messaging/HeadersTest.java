package br.com.eventhorizon.common.messaging;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HeadersTest {

    private static final String HEADER0_NAME = "header0";

    private static final String HEADER1_NAME = "header1";

    private static final String HEADER2_NAME = "header2";

    private static final String HEADER3_NAME = "header3";

    private static final Set<String> HEADER_NAMES = Set.of(HEADER0_NAME, HEADER1_NAME, HEADER2_NAME, HEADER3_NAME);

    private static final List<String> HEADER0_VALUES = Collections.emptyList();

    private static final List<String> HEADER1_VALUES = List.of("header1-value1");

    private static final List<String> HEADER2_VALUES = List.of("header2-value1", "header2-value2");

    private static final List<String> HEADER3_VALUES = List.of("header3-value1", "header3-value2", "header3-value3");

    private static final Headers HEADERS = Headers.builder()
            .header("header0", HEADER0_VALUES)
            .header("header1", HEADER1_VALUES)
            .header("header2", HEADER2_VALUES)
            .header("header3", HEADER3_VALUES)
            .build();

    @Test
    public void testToString() {
        // Given
        var headers = Headers.builder()
                .header("header1", "header1-value1")
                .build();

        // When
        var string = headers.toString();

        // Then
        assertTrue(string.contains("header1"));
        assertTrue(string.contains("header1-value1"));
    }

    @Test
    public void testHashCode() {
        // Given
        var headers = Headers.builder()
                .headers(HEADERS)
                .build();

        // When
        var hashCode = headers.hashCode();

        // Then
        assertEquals(HEADERS.hashCode(), hashCode);
    }

    @Test
    public void testNames() {
        Headers headers = Headers.builder().build();
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());

        headers = Headers.builder()
                .headers(HEADERS)
                .build();
        assertNotNull(headers.names());
        assertFalse(headers.names().isEmpty());
        assertEquals(HEADER_NAMES, headers.names());
    }

    @Test
    public void testValues() {
        Headers headers = Headers.builder().build();
        assertTrue(headers.values(HEADER0_NAME).isEmpty());
        assertTrue(headers.values(HEADER1_NAME).isEmpty());
        assertTrue(headers.values(HEADER2_NAME).isEmpty());
        assertTrue(headers.values(HEADER3_NAME).isEmpty());

        headers = Headers.builder().headers(HEADERS).build();
        assertNotNull(headers.values(HEADER0_NAME));
        assertTrue(headers.values(HEADER0_NAME).isEmpty());
        assertEquals(HEADER0_VALUES, headers.values(HEADER0_NAME));
        assertNotNull(headers.values(HEADER1_NAME));
        assertFalse(headers.values(HEADER1_NAME).isEmpty());
        assertEquals(HEADER1_VALUES, headers.values(HEADER1_NAME));
        assertNotNull(headers.values(HEADER2_NAME));
        assertFalse(headers.values(HEADER2_NAME).isEmpty());
        assertEquals(HEADER2_VALUES, headers.values(HEADER2_NAME));
        assertNotNull(headers.values(HEADER3_NAME));
        assertFalse(headers.values(HEADER3_NAME).isEmpty());
        assertEquals(HEADER3_VALUES, headers.values(HEADER3_NAME));
    }

    @Test
    public void testFirstValue() {
        // Given
        var headers = Headers.builder().headers(HEADERS).build();

        // Then
        assertFalse(headers.firstValue(HEADER0_NAME).isPresent());
        assertTrue(headers.firstValue(HEADER1_NAME).isPresent());
        assertEquals("header1-value1", headers.firstValue(HEADER1_NAME).get());
        assertTrue(headers.firstValue(HEADER2_NAME).isPresent());
        assertEquals("header2-value1", headers.firstValue(HEADER2_NAME).get());
        assertTrue(headers.firstValue(HEADER3_NAME).isPresent());
        assertEquals("header3-value1", headers.firstValue(HEADER3_NAME).get());
    }

    @Test
    public void testEmptyHeaders() {
        // Given
        var headers = Headers.emptyHeaders();

        // Then
        assertNotNull(headers);
        assertTrue(headers.isEmpty());
        assertSame(Headers.EMPTY_HEADERS, headers);
    }

    @Test
    public void testIterator() {
        // Given
        var iterator = Headers.builder()
                .headers(HEADERS)
                .build()
                .iterator();
        var names = new HashSet<>(Set.of("header0", "header1", "header2", "header3"));

        // Then
        for (int i = 0; i < 4; i++) {
            assertTrue(iterator.hasNext());
            var entry = iterator.next();
            assertTrue(names.contains(entry.getKey()));
            names.remove(entry.getKey());
            assertThrows(UnsupportedOperationException.class, () -> entry.setValue(List.of("value")));
        }
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testBuildEmptyHeaders() {
        Headers headers = Headers.builder().build();
        assertTrue(headers.isEmpty());
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());

        headers = Headers.builder()
                .headers(Headers.builder().build())
                .build();
        assertTrue(headers.isEmpty());
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());
    }

    @Test
    public void testBuildNonEmptyHeaders() {
        // When
        Headers headers = Headers.builder()
                .header("header0", Collections.emptyList())
                .header("header1", List.of("header1-value1", "header1-value1"))
                .header("header2", List.of("header2-value1", "header2-value1"))
                .header("header3", "header3-value1")
                .header("header4", "header4-value1")
                .header("header4", "header4-value2")
                .build();

        // Then
        assertFalse(headers.isEmpty());
        assertNotNull(headers.names());
        assertFalse(headers.names().isEmpty());
        assertEquals(Set.of("header0", "header1", "header2", "header3", "header4"), headers.names());
        assertTrue(headers.contains("header0"));
        assertTrue(headers.contains("header1"));
        assertTrue(headers.contains("header2"));
        assertTrue(headers.contains("header3"));
        assertTrue(headers.contains("header4"));
        assertEquals(Collections.emptyList(), headers.values("header0"));
        assertEquals(List.of("header1-value1", "header1-value1"), headers.values("header1"));
        assertEquals(List.of("header2-value1", "header2-value1"), headers.values("header2"));
        assertEquals(List.of("header3-value1"), headers.values("header3"));
        assertEquals(List.of("header4-value1", "header4-value2"), headers.values("header4"));
    }
}
