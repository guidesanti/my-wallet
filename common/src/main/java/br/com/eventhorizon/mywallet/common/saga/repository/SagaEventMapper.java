package br.com.eventhorizon.mywallet.common.saga.repository;

import br.com.eventhorizon.mywallet.common.saga.SagaEvent;
import br.com.eventhorizon.mywallet.common.saga.SagaHeaders;
import br.com.eventhorizon.mywallet.common.saga.SagaIdempotenceId;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;

import java.time.OffsetDateTime;

import static br.com.eventhorizon.mywallet.common.saga.Conventions.DEFAULT_DATE_TIME_FORMATTER;

public final class SagaEventMapper {

    public static SagaEventEntity modelToEntity(SagaEvent event, SagaContentSerializer serializer) {
        var builder = SagaEventEntity.builder()
                .idempotenceId(event.idempotenceId().toString())
                .traceId(event.traceId())
                .source(event.source())
                .destination(event.destination())
                .createdAt(event.createdAt().format(DEFAULT_DATE_TIME_FORMATTER))
                .replyOkTo(event.replyOkTo())
                .replyNotOkTo(event.replyNotOkTo())
                .content(serializer.serialize(event.content()))
                .contentType(event.content().getContent().getClass().getName());
        event.headers().forEach(header -> builder.header(header.getKey(), header.getValue()));
        return builder.build();
    }

    public static SagaEvent entityToModel(SagaEventEntity event, SagaContentSerializer serializer) {
        return SagaEvent.builder()
                .idempotenceId(SagaIdempotenceId.of(event.getIdempotenceId()))
                .traceId(event.getTraceId())
                .source(event.getSource())
                .destination(event.getDestination())
                .createdAt(OffsetDateTime.parse(event.getCreatedAt(), DEFAULT_DATE_TIME_FORMATTER))
                .replyOkTo(event.getReplyOkTo())
                .replyNotOkTo(event.getReplyNotOkTo())
                .headers(SagaHeaders.builder().headers(event.getHeaders()).build())
                .content(serializer.deserialize(event.getContent()))
                .build();
    }
}
