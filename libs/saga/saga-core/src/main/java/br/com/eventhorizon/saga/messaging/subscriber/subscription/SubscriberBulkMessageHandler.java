package br.com.eventhorizon.saga.messaging.subscriber.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SubscriberBulkMessageHandler<R, M> extends SubscriberMessageHandler<R, M> implements BulkMessageHandler<M> {

    public SubscriberBulkMessageHandler(SagaTransactionExecutor sagaTransactionExecutor, SagaTransaction<R, M> sagaTransaction) {
        super(sagaTransactionExecutor, sagaTransaction);
    }

    @Override
    public void handle(List<SubscriberMessage<M>> subscriberMessages) throws Exception {
        if (subscriberMessages.isEmpty()) {
            return;
        }
        List<SagaMessage<M>> sagaMessages = new ArrayList<>();
        subscriberMessages.forEach(subscriberMessage -> {
            try {
                sagaMessages.add(toSagaMessage(subscriberMessage));
            } catch (Exception ex) {
                log.error("Error while handling subscriber message: " + ex.getMessage(), ex);
                // TODO: Send message to DLQ
            }
        });
        log.info("Handling SAGA messages: {}", sagaMessages);
        sagaTransactionExecutor.execute(sagaTransaction, sagaMessages);
    }
}
