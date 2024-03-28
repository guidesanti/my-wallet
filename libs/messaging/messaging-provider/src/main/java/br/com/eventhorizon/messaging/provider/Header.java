package br.com.eventhorizon.messaging.provider;

import br.com.eventhorizon.common.Common;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Header {

    CREATED_AT(Header.PREFIX + "-created-at", "The date/time at the moment when the message were created. Should be kept the original in case the message is republished or sent to DLQ."),
    PUBLISHED_AT(Header.PREFIX + "-published-at", "The date/time at the moment when the message was published. Should be generated on every message sent, even for republished or sent to DLQ."),
    TRACE_ID(Header.PREFIX + "-trace-id", "The message trace ID to help debugging, troubleshooting, tracing, etc."),
    PUBLISHER(Header.PREFIX + "-publisher", "The publisher name."),
    RETRY_COUNT(Header.PREFIX + "-retry-count", "The number of times the message was resent to source or to retry source."),
    ERROR_CATEGORY(Header.PREFIX + "-error-category", "The error category when processing of the message fails. Generally added when message is sent to DLQ."),
    ERROR_CODE(Header.PREFIX + "-error-code", "The error code when processing of the message fails. Generally added when message is sent to DLQ."),
    ERROR_MESSAGE(Header.PREFIX + "-error-message", "The error message when processing of the message fails. Generally added when message is sent to DLQ."),
    ERROR_STACK_TRACE(Header.PREFIX + "-error-stack-trace", "The error stack trace when processing of the message fails. Generally added when message is sent to DLQ.");

    public static final String PREFIX = Common.PLATFORM_PREFIX;

    private final String name;

    private final String description;
}
