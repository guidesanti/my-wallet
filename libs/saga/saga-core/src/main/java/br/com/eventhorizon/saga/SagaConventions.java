package br.com.eventhorizon.saga;

public class SagaConventions {

    public static final String HEADER_CREATED_AT = "saga-created-at";

    public static final String HEADER_PUBLISHED_AT = "saga-published-at";

    public static final String HEADER_ORIGINAL_IDEMPOTENCE_ID = "saga-original-idempotence-id";

    public static final String HEADER_IDEMPOTENCE_ID = "saga-idempotence-id";

    public static final String HEADER_TRACE_ID = "saga-trace-id";

    public static final String HEADER_PUBLISHER = "saga-publisher";

    public static final String HEADER_PUBLISH_COUNT = "saga-publish-count";

    public static final String HEADER_REPLY_OK_TO = "saga-reply-ok-to";

    public static final String HEADER_REPLY_NOT_OK_TO = "saga-reply-not-ok-to";

    public static final String HEADER_ERROR_CODE = "saga-error-code";

    public static final String HEADER_ERROR_MESSAGE = "saga-error-message";

    public static final String HEADER_ERROR_EXCEPTION = "saga-error-exception";
}
