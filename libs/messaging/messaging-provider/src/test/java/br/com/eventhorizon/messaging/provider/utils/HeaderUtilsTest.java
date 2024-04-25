package br.com.eventhorizon.messaging.provider.utils;

import br.com.eventhorizon.common.Common;
import br.com.eventhorizon.common.config.ConfigSource;
import br.com.eventhorizon.common.config.impl.DefaultConfig;
import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.messaging.provider.Header;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderUtilsTest {

    private static final String UNKNOWN = "unknown";

    private static final String APPLICATION_NAME = "application-name";

    private static final String TRACE_ID = "trace-id";

    @Test
    public void testBuildBasePlatformHeadersWithNull() {
        // When
        var result = HeaderUtils.buildBasePlatformHeaders(null, null);

        // Then
        assertNotNull(result);

        assertTrue(result.containsKey(Header.CREATED_AT.getName()));
        assertFalse(result.get(Header.CREATED_AT.getName()).isEmpty());
        assertEquals(UNKNOWN, result.get(Header.CREATED_AT.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHED_AT.getName()));
        assertFalse(result.get(Header.PUBLISHED_AT.getName()).isEmpty());
        assertTrue(OffsetDateTime.parse(result.get(Header.PUBLISHED_AT.getName()).get(0), Common.DEFAULT_DATE_TIME_FORMATTER).isBefore(OffsetDateTime.now()));

        assertTrue(result.containsKey(Header.TRACE_ID.getName()));
        assertFalse(result.get(Header.TRACE_ID.getName()).isEmpty());
        assertEquals(UNKNOWN, result.get(Header.TRACE_ID.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHER.getName()));
        assertFalse(result.get(Header.PUBLISHER.getName()).isEmpty());
        assertEquals(UNKNOWN, result.get(Header.PUBLISHER.getName()).get(0));
    }

    @Test
    public void testBuildBasePlatformHeadersWithNullHeaders() {
        // Given
        var config = DefaultConfig.builder().source(new CustomConfigSource()).build();

        // When
        var result = HeaderUtils.buildBasePlatformHeaders(config, null);

        // Then
        assertNotNull(result);

        assertTrue(result.containsKey(Header.CREATED_AT.getName()));
        assertFalse(result.get(Header.CREATED_AT.getName()).isEmpty());
        assertEquals(UNKNOWN, result.get(Header.CREATED_AT.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHED_AT.getName()));
        assertFalse(result.get(Header.PUBLISHED_AT.getName()).isEmpty());
        assertTrue(OffsetDateTime.parse(result.get(Header.PUBLISHED_AT.getName()).get(0), Common.DEFAULT_DATE_TIME_FORMATTER).isBefore(OffsetDateTime.now()));

        assertTrue(result.containsKey(Header.TRACE_ID.getName()));
        assertFalse(result.get(Header.TRACE_ID.getName()).isEmpty());
        assertEquals(UNKNOWN, result.get(Header.TRACE_ID.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHER.getName()));
        assertFalse(result.get(Header.PUBLISHER.getName()).isEmpty());
        assertEquals(APPLICATION_NAME, result.get(Header.PUBLISHER.getName()).get(0));
    }

    @Test
    public void testBuildBasePlatformHeadersWithNullConfig() {
        // Given
        var createdAt = Common.DEFAULT_DATE_TIME_FORMATTER.format(OffsetDateTime.now());
        var headers = Headers.builder()
                .header(Header.CREATED_AT.getName(), createdAt)
                .header(Header.TRACE_ID.getName(), TRACE_ID)
                .build();

        // When
        var result = HeaderUtils.buildBasePlatformHeaders(null, headers);

        // Then
        assertNotNull(result);

        assertTrue(result.containsKey(Header.CREATED_AT.getName()));
        assertFalse(result.get(Header.CREATED_AT.getName()).isEmpty());
        assertEquals(createdAt, result.get(Header.CREATED_AT.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHED_AT.getName()));
        assertFalse(result.get(Header.PUBLISHED_AT.getName()).isEmpty());
        assertTrue(OffsetDateTime.parse(result.get(Header.PUBLISHED_AT.getName()).get(0), Common.DEFAULT_DATE_TIME_FORMATTER).isBefore(OffsetDateTime.now()));

        assertTrue(result.containsKey(Header.TRACE_ID.getName()));
        assertFalse(result.get(Header.TRACE_ID.getName()).isEmpty());
        assertEquals(TRACE_ID, result.get(Header.TRACE_ID.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHER.getName()));
        assertFalse(result.get(Header.PUBLISHER.getName()).isEmpty());
        assertEquals(UNKNOWN, result.get(Header.PUBLISHER.getName()).get(0));
    }

    @Test
    public void testBuildBasePlatformHeaders() {
        // Given
        var config = DefaultConfig.builder().source(new CustomConfigSource()).build();
        var createdAt = Common.DEFAULT_DATE_TIME_FORMATTER.format(OffsetDateTime.now());
        var headers = Headers.builder()
                .header(Header.CREATED_AT.getName(), createdAt)
                .header(Header.TRACE_ID.getName(), TRACE_ID)
                .build();

        // When
        var result = HeaderUtils.buildBasePlatformHeaders(config, headers);

        // Then
        assertNotNull(result);

        assertTrue(result.containsKey(Header.CREATED_AT.getName()));
        assertFalse(result.get(Header.CREATED_AT.getName()).isEmpty());
        assertEquals(createdAt, result.get(Header.CREATED_AT.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHED_AT.getName()));
        assertFalse(result.get(Header.PUBLISHED_AT.getName()).isEmpty());
        assertTrue(OffsetDateTime.parse(result.get(Header.PUBLISHED_AT.getName()).get(0), Common.DEFAULT_DATE_TIME_FORMATTER).isBefore(OffsetDateTime.now()));

        assertTrue(result.containsKey(Header.TRACE_ID.getName()));
        assertFalse(result.get(Header.TRACE_ID.getName()).isEmpty());
        assertEquals(TRACE_ID, result.get(Header.TRACE_ID.getName()).get(0));

        assertTrue(result.containsKey(Header.PUBLISHER.getName()));
        assertFalse(result.get(Header.PUBLISHER.getName()).isEmpty());
        assertEquals(APPLICATION_NAME, result.get(Header.PUBLISHER.getName()).get(0));
    }

    @Test
    public void testExtractPlatformHeaders() {
        // Given
        Map<String, List<String>> platformHeaders = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            platformHeaders.put(Header.PREFIX + RandomStringUtils.randomAlphabetic(1, 100),
                    List.of(RandomStringUtils.randomAlphabetic(1, 100)));
        }
        Map<String, List<String>> customHeaders = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String headerName;
            do {
                headerName = RandomStringUtils.randomAlphabetic(Header.PREFIX.length(), 100);
            } while (headerName.startsWith(Header.PREFIX));
            customHeaders.put(headerName,
                    List.of(RandomStringUtils.randomAlphabetic(1, 100)));
        }

        // When
        var result = HeaderUtils.extractPlatformHeaders(Headers.builder()
                .headers(customHeaders)
                .headers(platformHeaders)
                .build());

        // Then
        assertNotNull(result);
        assertEquals(20, result.size());
        assertEquals(result.keySet(), platformHeaders.keySet());
    }

    @Test
    public void testExtractCustomHeaders() {
        // Given
        Map<String, List<String>> platformHeaders = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            platformHeaders.put(Header.PREFIX + RandomStringUtils.randomAlphabetic(1, 100),
                    List.of(RandomStringUtils.randomAlphabetic(1, 100)));
        }
        Map<String, List<String>> customHeaders = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            String headerName;
            do {
                headerName = RandomStringUtils.randomAlphabetic(Header.PREFIX.length(), 100);
            } while (headerName.startsWith(Header.PREFIX));
            customHeaders.put(headerName,
                    List.of(RandomStringUtils.randomAlphabetic(1, 100)));
        }

        // When
        var result = HeaderUtils.extractCustomHeaders(Headers.builder()
                .headers(customHeaders)
                .headers(platformHeaders)
                .build());

        // Then
        assertNotNull(result);
        assertEquals(30, result.size());
        assertEquals(result.keySet(), customHeaders.keySet());
    }

    private static class CustomConfigSource implements ConfigSource {

        private static final Map<String, String> PROPS = new HashMap<>();

        static {
            PROPS.put(Common.Env.APPLICATION_NAME, APPLICATION_NAME);
        }

        @Override
        public String getName() {
            return "custom-config-source";
        }

        @Override
        public String getValue(String name) {
            return PROPS.get(name);
        }
    }
}
