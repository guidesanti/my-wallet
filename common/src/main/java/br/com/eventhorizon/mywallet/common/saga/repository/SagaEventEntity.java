package br.com.eventhorizon.mywallet.common.saga.repository;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaEventEntity {

    @Id
    private String id;

    private String originalIdempotenceId;

    private String idempotenceId;

    private String traceId;

    private String source;

    private String destination;

    private String createdAt;

    private Date expireAt;

    private String replyOkTo;

    private String replyNotOkTo;

    @Singular
    private Map<String, List<String>> headers;

    private byte[] content;

    private String contentType;

    private int publishCount;
}
