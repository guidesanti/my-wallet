package br.com.eventhorizon.saga.messaging.subscriber.subscription;

import br.com.eventhorizon.common.exception.RefusedException;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriberSingleMessageHandler<R, M> extends SubscriberMessageHandler<R, M> implements SingleMessageHandler<M> {

    public SubscriberSingleMessageHandler(SagaTransactionExecutor sagaTransactionExecutor, SagaTransaction<R, M> sagaTransaction) {
        super(sagaTransactionExecutor, sagaTransaction);
    }

    @Override
    public void handle(SubscriberMessage<M> subscriberMessage) throws Exception {
        SagaIdempotenceId idempotenceId = null;
        String traceId = null;
        try {
            var sagaMessage = toSagaMessage(subscriberMessage);
            idempotenceId = sagaMessage.idempotenceId();
            traceId = sagaMessage.traceId();
            sagaTransactionExecutor.execute(sagaTransaction, sagaMessage);
        } catch (RefusedException ex) {
            log.error("sagaTransactionId='{}' messageHeaders='{}'", sagaTransaction.getId(), subscriberMessage.headers(), ex);
            sendToDlq(idempotenceId, traceId, subscriberMessage, ex);
        } catch (Exception ex) {
            log.error("Unexpected exception while while handling subscriber message: {}", ex.getMessage(), ex);
            sendToDlq(idempotenceId, traceId, subscriberMessage, ex);
        }
    }
}
