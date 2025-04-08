package br.com.eventhorizon.messaging.provider;

import br.com.eventhorizon.common.Common;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Attribute {

    LATENCY(Attribute.PREFIX + "-Latency", "Message latency");

    public static final String PREFIX = Common.PLATFORM_PREFIX_UPPER_CASE;

    private final String name;

    private final String description;
}
