package br.com.eventhorizon.saga.messaging.subscriber.subscription;

import br.com.eventhorizon.common.refusal.Refusal;
import br.com.eventhorizon.common.exception.ClientException;
import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.saga.*;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RequiredArgsConstructor
public abstract class SubscriberMessageHandler<R, M> {

    protected final SagaTransactionExecutor sagaTransactionExecutor;

    protected final SagaTransaction<R, M> sagaTransaction;

    protected SagaMessage<M> toSagaMessage(SubscriberMessage<M> subscriberMessage) {
        return SagaMessage.<M>builder()
                .copy(subscriberMessage)
                .idempotenceId(extractIdempotenceId(subscriberMessage))
                .traceId(subscriberMessage.headers().firstValue(SagaConventions.HEADER_TRACE_ID).orElse(null))
                .source(subscriberMessage.headers().firstValue(SagaConventions.HEADER_PUBLISHER).orElse(null))
                .build();
    }

    protected SagaIdempotenceId extractIdempotenceId(SubscriberMessage<M> message) {
        return message.headers()
                .firstValue(SagaConventions.HEADER_IDEMPOTENCE_ID)
                .map(s -> {
                    try {
                        return SagaIdempotenceId.of(s);
                    } catch (Exception ex) {
                        throw new ClientException(
                                Refusal.of(SagaError.IDEMPOTENCE_ID_INVALID.getCode(),
                                        SagaError.IDEMPOTENCE_ID_INVALID.getMessage(s))
                        );
                    }
                })
                .orElseThrow(() -> new ClientException(
                        Refusal.of(SagaError.IDEMPOTENCE_ID_NOT_PRESENT.getCode(),
                                SagaError.IDEMPOTENCE_ID_NOT_PRESENT.getMessage())
                ));
    }

    protected void sendToDlq(SagaIdempotenceId idempotenceId, String traceId, SubscriberMessage<M> subscriberMessage, Exception ex) {
        var dlq = sagaTransaction.getDlq();
        if (dlq != null) {
            try {
                var headersBuilder = Headers.builder().headers(subscriberMessage.headers());
                headersBuilder.header(SagaConventions.HEADER_ERROR_MESSAGE, ex.getMessage());
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                headersBuilder.header(SagaConventions.HEADER_ERROR_EXCEPTION, sw.toString());

                var publisher = sagaTransaction.getPublisher();

                publisher.publish(SagaEvent.builder()
                        .originalIdempotenceId(idempotenceId != null ? idempotenceId : SagaIdempotenceId.NULL)
                        .idempotenceId(idempotenceId != null ? idempotenceId : SagaIdempotenceId.NULL)
                        .traceId(traceId)
                        .destination(dlq)
                        .headers(headersBuilder.build())
                        .content(subscriberMessage.content())
                        .build(), sagaTransaction.getSerializer());
            } catch (Exception ex1) {
                log.error("Failed to publish to DLQ: " + ex1.getMessage(), ex1);
            }
        }
    }
}
