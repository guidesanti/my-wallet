package br.com.eventhorizon.saga.messaging;

import br.com.eventhorizon.saga.Conventions;
import br.com.eventhorizon.saga.SagaEvent;
import br.com.eventhorizon.common.messaging.impl.DefaultHeaders;
import br.com.eventhorizon.common.messaging.impl.DefaultMessage;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhotizon.messaging.provider.publisher.PublisherRequest;
import br.com.eventhotizon.messaging.provider.publisher.TransactionablePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Component
public class DefaultSagaPublisher implements SagaPublisher {

    @Value("${br.com.eventhorizon.mywallet.service.name}")
    private String source;

    private final TransactionablePublisher publisher;

    @Override
    public void publish(SagaEvent event, SagaContentSerializer serializer) {
        var headersBuilder = DefaultHeaders.builder()
                .header(Conventions.HEADER_IDEMPOTENCE_ID, event.idempotenceId().toString())
                .header(Conventions.HEADER_TRACE_ID, event.traceId())
                .header(Conventions.HEADER_SOURCE, source)
                .header(Conventions.HEADER_CREATED_AT, event.createdAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .header(Conventions.HEADER_PUBLISHED_AT, OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .header(Conventions.HEADER_PUBLISH_COUNT, String.valueOf(event.publishCount() + 1));
        if (event.replyOkTo() != null) {
            headersBuilder.header(Conventions.HEADER_REPLY_OK_TO, event.replyOkTo());
        }
        if (event.replyNotOkTo() != null) {
            headersBuilder.header(Conventions.HEADER_REPLY_NOT_OK_TO, event.replyNotOkTo());
        }
        event.headers().forEach(entry -> {
            entry.getValue().forEach(value -> headersBuilder.header(entry.getKey(), value));
        });

        var message = DefaultMessage.builder()
                .headers(headersBuilder.build())
                .content(serializer.serialize(event.content()))
                .build();

        var request = PublisherRequest.builder()
                .destination(event.destination())
                .message(message)
                .build();

        publisher.publishAsync(request);
    }

    @Override
    public <T> T transact(Callable<T> task) throws Exception {
        return publisher.transact(task);
    }
}
