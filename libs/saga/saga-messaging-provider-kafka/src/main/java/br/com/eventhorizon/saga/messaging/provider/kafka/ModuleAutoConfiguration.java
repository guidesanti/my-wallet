package br.com.eventhorizon.saga.messaging.provider.kafka;

import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public KafkaSagaSubscriptionFactory kafkaSagaSubscriptionFactory(SagaTransactionExecutor sagaTransactionExecutor) {
        return new KafkaSagaSubscriptionFactory(sagaTransactionExecutor);
    }
}
