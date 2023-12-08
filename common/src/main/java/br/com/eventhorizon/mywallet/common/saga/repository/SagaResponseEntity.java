package br.com.eventhorizon.mywallet.common.saga.repository;

import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static br.com.eventhorizon.mywallet.common.saga.Conventions.DEFAULT_DATE_TIME_FORMATTER;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaResponseEntity {

    @NonNull
    private String idempotenceId;

    @NonNull
    @Builder.Default
    private String createdAt = OffsetDateTime.now(ZoneOffset.UTC).format(DEFAULT_DATE_TIME_FORMATTER);

    private Date expireAt;

    @Singular
    private final Map<String, List<String>> headers;

    private byte @NonNull [] content;

    @NonNull
    private String contentType;
}
