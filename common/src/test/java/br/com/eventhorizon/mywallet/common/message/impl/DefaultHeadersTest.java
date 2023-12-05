package br.com.eventhorizon.mywallet.common.message.impl;

import br.com.eventhorizon.mywallet.common.message.Headers;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultHeadersTest {

    private static final String HEADER0_NAME = "header0";

    private static final String HEADER1_NAME = "header1";

    private static final String HEADER2_NAME = "header2";

    private static final String HEADER3_NAME = "header3";

    private static final Set<String> HEADER_NAMES = Set.of(HEADER0_NAME, HEADER1_NAME, HEADER2_NAME, HEADER3_NAME);

    private static final List<String> HEADER0_VALUES = Collections.emptyList();

    private static final List<String> HEADER1_VALUES = List.of("header1-value1");

    private static final List<String> HEADER2_VALUES = List.of("header2-value1", "header2-value2");

    private static final List<String> HEADER3_VALUES = List.of("header3-value1", "header3-value2", "header3-value3");

    private static final Map<String, List<String>> HEADERS = Map.of(
            "header0", HEADER0_VALUES,
            "header1", HEADER1_VALUES,
            "header2", HEADER2_VALUES,
            "header3", HEADER3_VALUES
    );

    @Test
    public void testBuildEmptyHeaders() {
        Headers headers = DefaultHeaders.builder()
                .build();
        assertTrue(headers.isEmpty());
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());

        headers = DefaultHeaders.builder()
                .headers(null)
                .build();
        assertTrue(headers.isEmpty());
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());

        headers = DefaultHeaders.builder()
                .headers(Collections.emptyMap())
                .build();
        assertTrue(headers.isEmpty());
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());
    }

    @Test
    public void testBuildNonEmptyHeaders() {
        Headers headers = DefaultHeaders.builder()
                .headers(Map.of(
                        "header0", Collections.emptyList(),
                        "header1", List.of("header1-value1", "header1-value1"),
                        "header2", List.of("header2-value1", "header2-value1")))
                .build();
        assertFalse(headers.isEmpty());
        assertNotNull(headers.names());
        assertFalse(headers.names().isEmpty());
        assertEquals(Set.of("header0", "header1", "header2"), headers.names());
        assertTrue(headers.contains("header0"));
        assertTrue(headers.contains("header1"));
        assertTrue(headers.contains("header2"));
        assertFalse(headers.contains("header3"));
        assertEquals(Collections.emptyList(), headers.values("header0"));
        assertEquals(List.of("header1-value1", "header1-value1"), headers.values("header1"));
        assertEquals(List.of("header2-value1", "header2-value1"), headers.values("header2"));
    }

    @Test
    public void testNames() {
        Headers headers = DefaultHeaders.builder()
                .build();
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());

        headers = DefaultHeaders.builder()
                .headers(null)
                .build();
        assertNotNull(headers.names());
        assertTrue(headers.names().isEmpty());

        headers = DefaultHeaders.builder()
                .headers(HEADERS)
                .build();
        assertNotNull(headers.names());
        assertFalse(headers.names().isEmpty());
        assertEquals(HEADER_NAMES, headers.names());
    }

    @Test
    public void testValues() {
        Headers headers = DefaultHeaders.builder()
                .build();
        assertNull(headers.values(HEADER0_NAME));
        assertNull(headers.values(HEADER1_NAME));
        assertNull(headers.values(HEADER2_NAME));
        assertNull(headers.values(HEADER3_NAME));

        headers = DefaultHeaders.builder()
                .headers(null)
                .build();
        assertNull(headers.values(HEADER0_NAME));
        assertNull(headers.values(HEADER1_NAME));
        assertNull(headers.values(HEADER2_NAME));
        assertNull(headers.values(HEADER3_NAME));

        headers = DefaultHeaders.builder()
                .headers(HEADERS)
                .build();
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
}
