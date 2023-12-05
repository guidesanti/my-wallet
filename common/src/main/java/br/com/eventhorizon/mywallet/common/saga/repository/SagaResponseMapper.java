package br.com.eventhorizon.mywallet.common.saga.repository;

import br.com.eventhorizon.mywallet.common.saga.SagaHeaders;
import br.com.eventhorizon.mywallet.common.saga.SagaIdempotenceId;
import br.com.eventhorizon.mywallet.common.saga.SagaResponse;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;

import java.time.OffsetDateTime;

import static br.com.eventhorizon.mywallet.common.saga.Conventions.DEFAULT_DATE_TIME_FORMATTER;

public final class SagaResponseMapper {

    public static SagaResponseEntity modelToEntity(SagaResponse response, SagaContentSerializer serializer) {
        var builder = SagaResponseEntity.builder()
                .idempotenceId(response.idempotenceId().toString())
                .createdAt(response.createdAt().format(DEFAULT_DATE_TIME_FORMATTER))
                .content(serializer.serialize(response.content()))
                .contentType(response.content().getContent().getClass().getName());
        response.headers().forEach(header -> builder.header(header.getKey(), header.getValue()));
        return builder.build();
    }

    public static SagaResponse entityToModel(SagaResponseEntity response, SagaContentSerializer serializer) {
        return SagaResponse.builder()
                .idempotenceId(SagaIdempotenceId.of(response.getIdempotenceId()))
                .createdAt(OffsetDateTime.parse(response.getCreatedAt(), DEFAULT_DATE_TIME_FORMATTER))
                .headers(SagaHeaders.builder().headers(response.getHeaders()).build())
                .content(serializer.deserialize(response.getContent()))
                .build();
    }
}
