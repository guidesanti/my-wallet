package br.com.eventhorizon.saga;

import br.com.eventhorizon.saga.content.SagaContent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaMessage {

    @NonNull
    private final SagaIdempotenceId idempotenceId;

    @NonNull
    private final String traceId;

    private final String source;

    @NonNull
    @Builder.Default
    private final SagaHeaders headers = SagaHeaders.emptySagaHeaders();

    @NonNull
    private final SagaContent content;

    public SagaIdempotenceId idempotenceId() {
        return idempotenceId;
    }

    public String traceId() {
        return traceId;
    }

    public String source() {
        return source;
    }

    public SagaHeaders headers() {
        return headers;
    }

    public SagaContent content() {
        return content;
    }
}
