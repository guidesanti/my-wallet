package br.com.eventhorizon.messaging.provider;

import br.com.eventhorizon.common.Common;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Attribute {

    FETCH_TIME_MS(Attribute.PREFIX + "-Fetch-Time", "Message fetch time in milliseconds"),
    MESSAGE_POLLER_ID(Attribute.PREFIX + "-Mesage-Poller-Id", "Message poller ID"),
    KAFKA_TIMESTAMP(Attribute.PREFIX + "-Kafka-Timestamp", "Kafka timestamp"),
    KAFKA_TIMESTAMP_TYPE(Attribute.PREFIX + "-Kafka-Timestamp-Type", "Kafka timestamp type");

    public static final String PREFIX = Common.PLATFORM_PREFIX_UPPER_CASE;

    private final String name;

    private final String description;
}
