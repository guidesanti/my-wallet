package br.com.eventhorizon.saga;

import br.com.eventhorizon.common.messaging.Headers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaResponse<T> {

    @NonNull
    private final SagaIdempotenceId idempotenceId;

    @NonNull
    @Builder.Default
    private final OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    @NonNull
    @Builder.Default
    private final Headers headers = Headers.emptyHeaders();

    @NonNull
    private final T content;

    public SagaIdempotenceId idempotenceId() {
        return idempotenceId;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public Headers headers() {
        return headers;
    }

    public T content() {
        return content;
    }
}
