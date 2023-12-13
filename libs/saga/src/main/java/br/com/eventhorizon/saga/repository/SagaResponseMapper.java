package br.com.eventhorizon.saga.repository;

import br.com.eventhorizon.common.util.DateTimeUtils;
import br.com.eventhorizon.saga.SagaHeaders;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.SagaResponse;
import br.com.eventhorizon.saga.content.serializer.SagaContentSerializer;

import java.time.OffsetDateTime;

public final class SagaResponseMapper {

    public static SagaResponseEntity modelToEntity(SagaResponse response, SagaContentSerializer serializer) {
        var builder = SagaResponseEntity.builder()
                .idempotenceId(response.idempotenceId().toString())
                .createdAt(DateTimeUtils.offsetDateTimeToString(response.createdAt()))
                .content(serializer.serialize(response.content()))
                .contentType(response.content().getContent().getClass().getName());
        response.headers().forEach(header -> builder.header(header.getKey(), header.getValue()));
        return builder.build();
    }

    public static SagaResponse entityToModel(SagaResponseEntity response, SagaContentSerializer serializer) {
        return SagaResponse.builder()
                .idempotenceId(SagaIdempotenceId.of(response.getIdempotenceId()))
                .createdAt(DateTimeUtils.stringToOffsetDateTime(response.getCreatedAt()))
                .headers(SagaHeaders.builder().headers(response.getHeaders()).build())
                .content(serializer.deserialize(response.getContent()))
                .build();
    }
}
