package br.com.eventhorizon.messaging.provider;

import br.com.eventhorizon.common.Common;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Header {

    CREATED_AT(Header.PREFIX + "-Created-At", "The date/time at the moment when the message were created. Should be kept the original in case the message is republished or sent to DLQ."),
    PUBLISHED_AT(Header.PREFIX + "-Published-At", "The date/time at the moment when the message was published. Should be generated on every message sent, even for republished or sent to DLQ."),
    IDEMPOTENCE_ID(Header.PREFIX + "-Idempotence-ID", "The message idempotence ID"),
    TRACE_ID(Header.PREFIX + "-Trace-ID", "The message trace ID to help debugging, troubleshooting, tracing, etc."),
    PUBLISHER(Header.PREFIX + "-Publisher", "The publisher name."),
    RETRY_COUNT(Header.PREFIX + "-Retry-Count", "The number of times the message was resent to source or to retry source."),
    ERROR_MESSAGE(Header.PREFIX + "-Error-Message", "The error message when processing of the message fails. Generally added when message is sent to DLQ."),
    ERROR_STACK_TRACE(Header.PREFIX + "-Error-Stack-Trace", "The error stack trace when processing of the message fails. Generally added when message is sent to DLQ.");

    public static final String PREFIX = Common.PLATFORM_PREFIX_UPPER_CASE;

    private final String name;

    private final String description;
}
