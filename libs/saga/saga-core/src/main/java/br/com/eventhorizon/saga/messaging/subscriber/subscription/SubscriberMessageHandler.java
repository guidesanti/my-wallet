package br.com.eventhorizon.saga.messaging.subscriber.subscription;

import br.com.eventhorizon.common.exception.BaseErrorException;
import br.com.eventhorizon.common.exception.ClientErrorErrorException;
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
public abstract class SubscriberMessageHandler<T> {

    protected final SagaTransactionExecutor sagaTransactionExecutor;

    protected final SagaTransaction<T> sagaTransaction;

    protected SagaMessage<T> toSagaMessage(SubscriberMessage<T> subscriberMessage) {
        return SagaMessage.<T>builder()
                .idempotenceId(extractIdempotenceId(subscriberMessage))
                .traceId(subscriberMessage.headers().firstValue(SagaConventions.HEADER_TRACE_ID).orElse(null))
                .source(subscriberMessage.headers().firstValue(SagaConventions.HEADER_PUBLISHER).orElse(null))
                .headers(subscriberMessage.headers())
                .content(subscriberMessage.content())
                .build();
    }

    protected SagaIdempotenceId extractIdempotenceId(SubscriberMessage<T> message) {
        return message.headers()
                .firstValue(SagaConventions.HEADER_IDEMPOTENCE_ID)
                .map(s -> {
                    try {
                        return SagaIdempotenceId.of(s);
                    } catch (Exception ex) {
                        throw new ClientErrorErrorException(
                                SagaError.IDEMPOTENCE_ID_INVALID.getCode(),
                                SagaError.IDEMPOTENCE_ID_INVALID.getMessage(s)
                        );
                    }
                })
                .orElseThrow(() -> new ClientErrorErrorException(
                        SagaError.IDEMPOTENCE_ID_NOT_PRESENT.getCode(),
                        SagaError.IDEMPOTENCE_ID_NOT_PRESENT.getMessage()
                ));
    }

    protected void sendToDlq(SagaIdempotenceId idempotenceId, String traceId, SubscriberMessage<T> subscriberMessage, Exception ex) {
        var dlq = sagaTransaction.getDlq();
        if (dlq != null) {
            try {
                var headersBuilder = subscriberMessage.headers().toBuilder();
                if (ex instanceof BaseErrorException baseErrorException) {
                    headersBuilder.header(SagaConventions.HEADER_ERROR_CODE, baseErrorException.getErrorCode().toString());
                    headersBuilder.header(SagaConventions.HEADER_ERROR_MESSAGE, baseErrorException.getErrorMessage());
                }
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
