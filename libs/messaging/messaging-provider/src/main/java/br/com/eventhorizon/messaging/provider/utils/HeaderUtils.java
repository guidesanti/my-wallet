package br.com.eventhorizon.messaging.provider.utils;

import br.com.eventhorizon.common.Common;
import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.messaging.provider.Header;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HeaderUtils {

    public static Map<String, List<String>> buildBasePlatformHeaders(Config config, Headers headers) {
        var platformHeaders = new HashMap<String, List<String>>();
        platformHeaders.put(Header.CREATED_AT.getName(), List.of(getCreatedAtHeader(headers)));
        platformHeaders.put(Header.PUBLISHED_AT.getName(), List.of(OffsetDateTime.now().format(Common.DEFAULT_DATE_TIME_FORMATTER)));
        platformHeaders.put(Header.TRACE_ID.getName(), List.of(getTraceIdHeader(headers)));
        platformHeaders.put(Header.PUBLISHER.getName(), List.of(config.getOptionalValue(Common.Env.APPLICATION_NAME)
                .or(() -> config.getOptionalValue(Common.SysProp.APPLICATION_NAME))
                .orElse("unknown")));
        return platformHeaders;
    }

    public static Map<String, List<String>> extractPlatformHeaders(Headers headers) {
        var platformHeaders = new HashMap<String, List<String>>();
        headers.forEach(entry -> {
            if (isPlatformHeader(entry.getKey())) {
                platformHeaders.put(entry.getKey(), entry.getValue());
            }
        });
        return platformHeaders;
    }

    public static Map<String, List<String>> extractCustomHeaders(Headers headers) {
        var customHeaders = new HashMap<String, List<String>>();
        headers.forEach(entry -> {
            if (isCustomHeader(entry.getKey())) {
                customHeaders.put(entry.getKey(), entry.getValue());
            }
        });
        return customHeaders;
    }

    public static boolean isPlatformHeader(String headerName) {
        return StringUtils.startsWith(headerName, Header.PREFIX);
    }

    public static boolean isCustomHeader(String headerName) {
        return !StringUtils.startsWith(headerName, Header.PREFIX);
    }

    private static String getCreatedAtHeader(Headers headers) {
        return headers != null
                ? headers.firstValue(Header.CREATED_AT.getName()).orElse(StringUtils.EMPTY)
                : StringUtils.EMPTY;
    }

    private static String getTraceIdHeader(Headers headers) {
        String traceId = null;

        if (headers != null) {
            traceId = headers.firstValue(Header.TRACE_ID.getName()).orElse(null);
        }
        if (traceId == null) {
            traceId = MDC.get(Common.MDCKey.TRACE_ID);
        }
        if (traceId == null) {
            traceId = StringUtils.EMPTY;
        }

        return traceId;
    }
}
