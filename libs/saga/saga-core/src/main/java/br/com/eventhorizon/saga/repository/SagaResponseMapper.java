package br.com.eventhorizon.saga.repository;

import br.com.eventhorizon.common.error.DefaultErrors;
import br.com.eventhorizon.common.exception.ServerErrorErrorException;
import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.common.util.DateTimeUtils;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.SagaResponse;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SagaResponseMapper {

    public static <T> SagaResponseEntity modelToEntity(SagaResponse<T> response, SagaContentSerializer serializer) {
        var builder = SagaResponseEntity.builder()
                .idempotenceId(response.idempotenceId().toString())
                .createdAt(DateTimeUtils.offsetDateTimeToString(response.createdAt()))
                .content(serializer.serialize(response.content()))
                .contentType(response.content().getClass().getName());
        response.headers().forEach(header -> builder.header(header.getKey(), header.getValue()));
        return builder.build();
    }

    public static <T> SagaResponse<T> entityToModel(SagaResponseEntity response, SagaContentSerializer serializer) {
        try {
            return SagaResponse.<T>builder()
                    .idempotenceId(SagaIdempotenceId.of(response.getIdempotenceId()))
                    .createdAt(DateTimeUtils.stringToOffsetDateTime(response.getCreatedAt()))
                    .headers(Headers.builder().headers(response.getHeaders()).build())
                    .content(serializer.deserialize(response.getContent(), (Class<T>) Class.forName(response.getContentType())))
                    .build();
        } catch (ClassNotFoundException e) {
            var message = "Cannot map SAGA response from repository model to business model, invalid content type " + response.getContentType();
            log.error(message, e);
            throw new ServerErrorErrorException(DefaultErrors.UNEXPECTED_SERVER_ERROR.getCode(), message);
        }
    }
}
