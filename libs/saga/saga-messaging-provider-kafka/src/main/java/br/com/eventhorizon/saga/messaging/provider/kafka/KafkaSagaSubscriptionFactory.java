package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.messaging.provider.kafka.subscription.KafkaBulkSubscription;
import br.com.eventhorizon.messaging.provider.kafka.subscription.KafkaSingleSubscription;
import br.com.eventhorizon.messaging.provider.subscription.Subscription;
import br.com.eventhorizon.saga.handler.SagaBulkHandler;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SagaSubscriptionFactory;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SubscriberBulkMessageHandler;
import br.com.eventhorizon.saga.messaging.subscriber.subscription.SubscriberSingleMessageHandler;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import br.com.eventhorizon.saga.transaction.UnsupportedSagaTransactionException;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RequiredArgsConstructor
public class KafkaSagaSubscriptionFactory implements SagaSubscriptionFactory {

    private final SagaTransactionExecutor sagaTransactionExecutor;

    @Override
    public String getProviderName() {
        return Conventions.PROVIDER_NAME;
    }

    @Override
    public <R, M> Subscription<M> create(SagaTransaction<R, M> sagaTransaction) {
        if (sagaTransaction instanceof KafkaSagaTransaction<R, M> kafkaSagaTransaction) {
            if (kafkaSagaTransaction.getHandler() instanceof SagaSingleHandler<R, M>) {
                return KafkaSingleSubscription.<M>builder()
                        .id(String.format("%s-kafka-subscription", kafkaSagaTransaction.getId()))
                        .handler(new SubscriberSingleMessageHandler<>(sagaTransactionExecutor, sagaTransaction))
                        .source(kafkaSagaTransaction.getSource())
                        .sourceType(kafkaSagaTransaction.getSourceType())
                        .configs(kafkaSagaTransaction.getKafkaConsumerConfig())
                        .build();
            }

            if (kafkaSagaTransaction.getHandler() instanceof SagaBulkHandler<R, M>) {
                return KafkaBulkSubscription.<M>builder()
                        .id(String.format("%s-kafka-subscription", kafkaSagaTransaction.getId()))
                        .handler(new SubscriberBulkMessageHandler<>(sagaTransactionExecutor, sagaTransaction))
                        .source(kafkaSagaTransaction.getSource())
                        .sourceType(kafkaSagaTransaction.getSourceType())
                        .configs(kafkaSagaTransaction.getKafkaConsumerConfig())
                        .build();
            }
        }

        throw new UnsupportedSagaTransactionException(this, sagaTransaction);
    }
}
