package br.com.eventhorizon.mywallet.common.saga.repository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaTransactionEntity {

    private final String idempotenceId;

    private final String checksum;

    private final String createdAt;
}
