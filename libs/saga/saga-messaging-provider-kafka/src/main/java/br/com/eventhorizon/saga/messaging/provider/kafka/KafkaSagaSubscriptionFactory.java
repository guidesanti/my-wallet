package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription.DefaultKafkaBulkSubscription;
import br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription.DefaultKafkaSingleSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import br.com.eventhorizon.saga.handler.SagaBulkHandler;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SagaSubscriptionFactory;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.UnsupportedSagaTransactionException;

public class KafkaSagaSubscriptionFactory implements SagaSubscriptionFactory {

    @Override
    public String getProviderName() {
        return Conventions.PROVIDER_NAME;
    }

    @Override
    public <T> Subscription<T> create(SagaTransaction<T> sagaTransaction) {
        if (sagaTransaction instanceof KafkaSagaTransaction<T> kafkaSagaTransaction) {
            if (kafkaSagaTransaction.getHandler() instanceof SagaSingleHandler sagaSingleHandler) {
                return new DefaultKafkaSingleSubscription<>(
                        String.format("%s-kafka-subscription", kafkaSagaTransaction.getId()),
                        new KafkaSagaSingleMessageHandler<>(sagaSingleHandler),
                        kafkaSagaTransaction.getSource(),
                        kafkaSagaTransaction.getSourceType(),
                        kafkaSagaTransaction.getConfig());
            }

            if (kafkaSagaTransaction.getHandler() instanceof SagaBulkHandler sagaBulkHandler) {
                return new DefaultKafkaBulkSubscription<>(
                        String.format("%s-kafka-subscription", kafkaSagaTransaction.getId()),
                        new KafkaSagaBulkMessageHandler<>(sagaBulkHandler),
                        kafkaSagaTransaction.getSource(),
                        kafkaSagaTransaction.getSourceType(),
                        kafkaSagaTransaction.getConfig());
            }
        }

        throw new UnsupportedSagaTransactionException(this, sagaTransaction);
    }
}
