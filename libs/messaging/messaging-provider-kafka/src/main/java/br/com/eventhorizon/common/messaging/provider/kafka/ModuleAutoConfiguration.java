package br.com.eventhorizon.common.messaging.provider.kafka;

import br.com.eventhorizon.common.messaging.provider.kafka.publisher.impl.KafkaPublisher;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public KafkaPublisher kafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaPublisher(kafkaTemplate);
    }
}
