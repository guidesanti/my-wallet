package br.com.eventhorizon.saga.messaging.provider.kafka;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public KafkaSagaSubscriptionFactory kafkaSagaSubscriptionFactory() {
        return new KafkaSagaSubscriptionFactory();
    }
}
