package br.com.eventhorizon.saga.repository;

import br.com.eventhorizon.common.error.DefaultErrors;
import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.exception.ServerErrorException;
import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.common.utils.DateTimeUtils;
import br.com.eventhorizon.saga.SagaEvent;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SagaEventMapper {

    public static <T> SagaEventEntity modelToEntity(SagaEvent<T> event, SagaContentSerializer serializer) {
        var builder = SagaEventEntity.builder()
                .id(event.id())
                .originalIdempotenceId(event.originalIdempotenceId().toString())
                .idempotenceId(event.idempotenceId().toString())
                .traceId(event.traceId())
                .destination(event.destination())
                .createdAt(DateTimeUtils.offsetDateTimeToString(event.createdAt()))
                .replyOkTo(event.replyOkTo())
                .replyNotOkTo(event.replyNotOkTo())
                .content(serializer.serialize(event.content()))
                .contentType(event.content().getClass().getName())
                .publishCount(event.publishCount());
        event.headers().forEach(header -> builder.header(header.getKey(), header.getValue()));
        return builder.build();
    }

    public static <T> SagaEvent<T> entityToModel(SagaEventEntity event, SagaContentSerializer serializer) {
        try {
            return SagaEvent.<T>builder()
                    .id(event.getId())
                    .originalIdempotenceId(SagaIdempotenceId.of(event.getOriginalIdempotenceId()))
                    .idempotenceId(SagaIdempotenceId.of(event.getIdempotenceId()))
                    .traceId(event.getTraceId())
                    .destination(event.getDestination())
                    .createdAt(DateTimeUtils.stringToOffsetDateTime(event.getCreatedAt()))
                    .replyOkTo(event.getReplyOkTo())
                    .replyNotOkTo(event.getReplyNotOkTo())
                    .headers(Headers.builder().headers(event.getHeaders()).build())
                    .content(serializer.deserialize(event.getContent(), (Class<T>) Class.forName(event.getContentType())))
                    .publishCount(event.getPublishCount())
                    .build();
        } catch (ClassNotFoundException e) {
            var message = "Cannot map SAGA event from repository model to business model, invalid content type " + event.getContentType();
            log.error(message, e);
            throw new ServerErrorException(Error.of(DefaultErrors.UNEXPECTED_SERVER_ERROR.getCode(), message));
        }
    }
}
