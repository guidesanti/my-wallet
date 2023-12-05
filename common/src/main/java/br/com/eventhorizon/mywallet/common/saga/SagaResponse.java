package br.com.eventhorizon.mywallet.common.saga;

import br.com.eventhorizon.mywallet.common.saga.content.SagaContent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaResponse {

    @NonNull
    private final SagaIdempotenceId idempotenceId;

    @NonNull
    @Builder.Default
    private final OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    @NonNull
    @Builder.Default
    private final SagaHeaders headers = SagaHeaders.emptySagaHeaders();

    @NonNull
    private final SagaContent content;

    public SagaIdempotenceId idempotenceId() {
        return idempotenceId;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public SagaHeaders headers() {
        return headers;
    }

    public SagaContent content() {
        return content;
    }
}
