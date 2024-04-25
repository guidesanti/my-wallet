package br.com.eventhorizon.saga.messaging.publisher;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.common.messaging.Message;
import br.com.eventhorizon.messaging.provider.publisher.Publisher;
import br.com.eventhorizon.saga.SagaConventions;
import br.com.eventhorizon.saga.SagaEvent;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.messaging.provider.publisher.PublishingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class DefaultSagaPublisher implements SagaPublisher {

    @Value("${br.com.eventhorizon.mywallet.service.name}")
    private String source;

    private final Publisher publisher;

    @Override
    public <T> void publish(SagaEvent<T> event, SagaContentSerializer serializer) {
        var headersBuilder = Headers.builder()
                .header(SagaConventions.HEADER_ORIGINAL_IDEMPOTENCE_ID, event.originalIdempotenceId().toString())
                .header(SagaConventions.HEADER_IDEMPOTENCE_ID, event.idempotenceId().toString())
                .header(SagaConventions.HEADER_PUBLISHER, source)
                .header(SagaConventions.HEADER_CREATED_AT, event.createdAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .header(SagaConventions.HEADER_PUBLISHED_AT, OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .header(SagaConventions.HEADER_PUBLISH_COUNT, String.valueOf(event.publishCount() + 1));
        if (event.traceId() != null) {
            headersBuilder.header(SagaConventions.HEADER_TRACE_ID, event.traceId());
        }
        if (event.replyOkTo() != null) {
            headersBuilder.header(SagaConventions.HEADER_REPLY_OK_TO, event.replyOkTo());
        }
        if (event.replyNotOkTo() != null) {
            headersBuilder.header(SagaConventions.HEADER_REPLY_NOT_OK_TO, event.replyNotOkTo());
        }
        event.headers().forEach(entry -> {
            entry.getValue().forEach(value -> headersBuilder.header(entry.getKey(), value));
        });

        var message = Message.builder()
                .headers(headersBuilder.build())
                .content(serializer.serialize(event.content()))
                .build();

        var request = PublishingRequest.builder()
                .destination(event.destination())
                .message(message)
                .build();

        publisher.publishAsync(request);
    }

    @Override
    public <T> T transact(Callable<T> task) throws Exception {
        return publisher.executeInTransaction(task);
    }
}
