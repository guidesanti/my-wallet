package br.com.eventhorizon.saga;

public class Conventions {

    public static final String HEADER_CREATED_AT = "saga-created-at";

    public static final String HEADER_PUBLISHED_AT = "saga-published-at";

    public static final String HEADER_IDEMPOTENCE_ID = "saga-idempotence-id";

    public static final String HEADER_TRACE_ID = "saga-trace-id";

    public static final String HEADER_SOURCE = "saga-source";

    public static final String HEADER_PUBLISH_COUNT = "saga-publish-count";

    public static final String HEADER_REPLY_OK_TO = "saga-reply-ok-to";

    public static final String HEADER_REPLY_NOT_OK_TO = "saga-reply-not-ok-to";
}
