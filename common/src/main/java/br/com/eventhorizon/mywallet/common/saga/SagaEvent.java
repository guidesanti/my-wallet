package br.com.eventhorizon.mywallet.common.saga;

import br.com.eventhorizon.mywallet.common.saga.content.SagaContent;
import br.com.eventhorizon.mywallet.common.util.IdUtil;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaEvent {

    @NonNull
    @Builder.Default
    private final String id = IdUtil.generateId(IdUtil.IdType.UUID);

    @NonNull
    private final SagaIdempotenceId originalIdempotenceId;

    @NonNull
    private final SagaIdempotenceId idempotenceId;

    private final String traceId;

    @NonNull
    private final String source;

    @NonNull
    private final String destination;

    @NonNull
    @Builder.Default
    private final OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    private final String replyOkTo;

    private final String replyNotOkTo;

    @NonNull
    @Builder.Default
    private final SagaHeaders headers = SagaHeaders.emptySagaHeaders();

    @NonNull
    private final SagaContent content;

    @Builder.Default
    private final int publishCount = 0;

    public String id() {
        return id;
    }

    public SagaIdempotenceId originalIdempotenceId() {
        return originalIdempotenceId;
    }

    public SagaIdempotenceId idempotenceId() {
        return idempotenceId;
    }

    public String traceId() {
        return traceId;
    }

    public String source() {
        return source;
    }

    public String destination() {
        return destination;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public String replyOkTo() {
        return replyOkTo;
    }

    public String replyNotOkTo() {
        return replyNotOkTo;
    }

    public SagaHeaders headers() {
        return headers;
    }

    public SagaContent content() {
        return content;
    }

    public int publishCount() {
        return publishCount;
    }
}
