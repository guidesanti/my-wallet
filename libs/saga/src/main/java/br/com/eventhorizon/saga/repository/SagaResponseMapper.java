package br.com.eventhorizon.saga.repository;

import br.com.eventhorizon.common.common.DefaultErrors;
import br.com.eventhorizon.common.exception.ServerErrorException;
import br.com.eventhorizon.common.util.DateTimeUtils;
import br.com.eventhorizon.saga.SagaHeaders;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.SagaResponse;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        try {
            return SagaResponse.builder()
                    .idempotenceId(SagaIdempotenceId.of(response.getIdempotenceId()))
                    .createdAt(DateTimeUtils.stringToOffsetDateTime(response.getCreatedAt()))
                    .headers(SagaHeaders.builder().headers(response.getHeaders()).build())
                    .content(serializer.deserialize(response.getContent(), Class.forName(response.getContentType())))
                    .build();
        } catch (ClassNotFoundException e) {
            var message = "Cannot map SAGA response from repository model to business model, invalid content type " + response.getContentType();
            log.error(message, e);
            throw new ServerErrorException(DefaultErrors.UNEXPECTED_SERVER_ERROR.getCode(), message);
        }
    }
}
