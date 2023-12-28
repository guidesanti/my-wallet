package br.com.eventhorizon.messaging.provider.kafka;

import br.com.eventhorizon.messaging.provider.kafka.publisher.impl.KafkaPublisher;
import br.com.eventhorizon.messaging.provider.kafka.subscriber.KafkaSubscriberFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutorService;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public KafkaPublisher kafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaPublisher(kafkaTemplate);
    }

    @Bean
    public KafkaSubscriberFactory kafkaSubscriberFactory(ExecutorService executorService) {
        return new KafkaSubscriberFactory(executorService);
    }
}
