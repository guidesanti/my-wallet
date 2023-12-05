package br.com.eventhorizon.mywallet.common.saga.message;

import br.com.eventhorizon.mywallet.common.message.Conventions;
import br.com.eventhorizon.mywallet.common.message.impl.DefaultHeaders;
import br.com.eventhorizon.mywallet.common.message.impl.DefaultMessage;
import br.com.eventhorizon.mywallet.common.message.publisher.PublisherRequest;
import br.com.eventhorizon.mywallet.common.message.publisher.TransactionablePublisher;
import br.com.eventhorizon.mywallet.common.saga.SagaEvent;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Component
public class DefaultSagaPublisher implements SagaPublisher {

    private final TransactionablePublisher publisher;

    @Override
    public void publish(SagaEvent event, Map<Class<?>, SagaContentSerializer> serializers) {
        var serde = findSerde(serializers, event.content().getContent().getClass());

        var headersBuilder = DefaultHeaders.builder()
                .header(Conventions.HEADER_IDEMPOTENCE_ID, event.idempotenceId().toString())
                .header(Conventions.HEADER_TRACE_ID, event.traceId())
                .header(Conventions.HEADER_SOURCE, event.source())
                .header(Conventions.HEADER_CREATED_AT, event.createdAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .header(Conventions.HEADER_PUBLISHED_AT, OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .header(Conventions.HEADER_RETRY_COUNT, String.valueOf(0));
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
                .content(serde.serialize(event.content()))
                .build();

        var request = PublisherRequest.builder()
                .destination(event.destination())
                .message(message)
                .build();

        publisher.publishAsync(request);
    }

    private SagaContentSerializer findSerde(Map<Class<?>, SagaContentSerializer> serializers, Class<?> type) {
        var serializer = serializers.get(type);
        if (serializer == null) {
            throw new RuntimeException("Implementation error, cannot find a serializer/deserializer for class " + type.getName());
        }
        return serializer;
    }

    @Override
    public <T> T transact(Callable<T> task) throws Exception {
        return publisher.transact(task);
    }
}
