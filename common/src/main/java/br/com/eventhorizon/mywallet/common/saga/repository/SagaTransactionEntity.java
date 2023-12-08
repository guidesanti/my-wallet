package br.com.eventhorizon.mywallet.common.saga.repository;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaTransactionEntity {

    private String idempotenceId;

    private String checksum;

    private String createdAt;

    private Date expireAt;
}
