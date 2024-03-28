package br.com.eventhorizon.saga.repository;

import br.com.eventhorizon.common.Common;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaResponseEntity {

    @NonNull
    private String idempotenceId;

    @NonNull
    @Builder.Default
    private String createdAt = OffsetDateTime.now(ZoneOffset.UTC).format(Common.DEFAULT_DATE_TIME_FORMATTER);

    private Date expireAt;

    @Singular
    private final Map<String, List<String>> headers;

    private byte @NonNull [] content;

    @NonNull
    private String contentType;
}
