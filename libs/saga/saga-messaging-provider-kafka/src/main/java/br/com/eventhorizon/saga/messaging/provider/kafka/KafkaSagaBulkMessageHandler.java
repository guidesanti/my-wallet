package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import br.com.eventhorizon.saga.Conventions;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.handler.SagaBulkHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class KafkaSagaBulkMessageHandler<T> implements BulkMessageHandler<T> {

    @NonNull
    private final SagaBulkHandler handler;

    @Override
    public void handle(List<SubscriberMessage<T>> messages) throws Exception {
        List<SagaMessage> sagaMessages = new ArrayList<>();
        
        messages.forEach(message -> {
            var headersBuilder = Headers.builder();
            message.headers().forEach(header -> header.getValue().forEach(value -> headersBuilder.header(header.getKey(), value)));
            sagaMessages.add(SagaMessage.builder()
                    .idempotenceId(message.headers().firstValue(Conventions.HEADER_IDEMPOTENCE_ID).map(SagaIdempotenceId::of).orElse(null))
                    .traceId(message.headers().firstValue(br.com.eventhorizon.saga.Conventions.HEADER_TRACE_ID).orElse(null))
                    .source(message.headers().firstValue(Conventions.HEADER_SOURCE).orElse(null))
                    .headers(headersBuilder.build())
                    .content(message.content())
                    .build());
        });

        handler.handle(sagaMessages);
    }
}
