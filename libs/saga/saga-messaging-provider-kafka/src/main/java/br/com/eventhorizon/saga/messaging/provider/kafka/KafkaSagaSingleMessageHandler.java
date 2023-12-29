package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import br.com.eventhorizon.saga.Conventions;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaSagaSingleMessageHandler<T> implements SingleMessageHandler<T> {

    @NonNull
    private final SagaSingleHandler handler;

    @Override
    public void handle(SubscriberMessage<T> message) throws Exception {
        var headersBuilder = Headers.builder();
        message.headers().forEach(header -> header.getValue().forEach(value -> headersBuilder.header(header.getKey(), value)));

        handler.handle(SagaMessage.builder()
                .idempotenceId(message.headers().firstValue(Conventions.HEADER_IDEMPOTENCE_ID).map(SagaIdempotenceId::of).orElse(null))
                .traceId(message.headers().firstValue(Conventions.HEADER_TRACE_ID).orElse(null))
                .source(message.headers().firstValue(Conventions.HEADER_SOURCE).orElse(null))
                .headers(headersBuilder.build())
                .content(message.content())
                .build());
    }
}
