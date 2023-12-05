package br.com.eventhorizon.mywallet.common.saga.repository;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaEventEntity {

    private final String idempotenceId;

    private final String traceId;

    private final String source;

    private final String destination;

    private final String createdAt;

    private final String replyOkTo;

    private final String replyNotOkTo;

    @Singular
    private final Map<String, List<String>> headers;

    private final byte[] content;

    private final String contentType;
}
