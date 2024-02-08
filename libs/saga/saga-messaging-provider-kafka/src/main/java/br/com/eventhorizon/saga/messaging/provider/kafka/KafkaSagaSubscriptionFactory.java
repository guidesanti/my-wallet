package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription.DefaultKafkaBulkSubscription;
import br.com.eventhorizon.messaging.provider.kafka.subscriber.subscription.DefaultKafkaSingleSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import br.com.eventhorizon.saga.handler.SagaBulkHandler;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SagaSubscriptionFactory;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SubscriberBulkMessageHandler;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SubscriberSingleMessageHandler;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import br.com.eventhorizon.saga.transaction.UnsupportedSagaTransactionException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaSagaSubscriptionFactory implements SagaSubscriptionFactory {

    private final SagaTransactionExecutor sagaTransactionExecutor;

    @Override
    public String getProviderName() {
        return Conventions.PROVIDER_NAME;
    }

    @Override
    public <T> Subscription<T> create(SagaTransaction<T> sagaTransaction) {
        if (sagaTransaction instanceof KafkaSagaTransaction<T> kafkaSagaTransaction) {
            if (kafkaSagaTransaction.getHandler() instanceof SagaSingleHandler<T>) {
                return new DefaultKafkaSingleSubscription<>(
                        String.format("%s-kafka-subscription", kafkaSagaTransaction.getId()),
                        new SubscriberSingleMessageHandler<>(sagaTransactionExecutor, sagaTransaction),
                        kafkaSagaTransaction.getSource(),
                        kafkaSagaTransaction.getSourceType(),
                        kafkaSagaTransaction.getKafkaConsumerConfig());
            }

            if (kafkaSagaTransaction.getHandler() instanceof SagaBulkHandler<T>) {
                return new DefaultKafkaBulkSubscription<>(
                        String.format("%s-kafka-subscription", kafkaSagaTransaction.getId()),
                        new SubscriberBulkMessageHandler<>(sagaTransactionExecutor, sagaTransaction),
                        kafkaSagaTransaction.getSource(),
                        kafkaSagaTransaction.getSourceType(),
                        kafkaSagaTransaction.getKafkaConsumerConfig());
            }
        }

        throw new UnsupportedSagaTransactionException(this, sagaTransaction);
    }
}
