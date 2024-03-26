package br.com.eventhorizon.mywallet.ms.assets.infrastructure.messaging;

import br.com.eventhorizon.messaging.provider.kafka.publisher.KafkaPublisher;
import br.com.eventhorizon.mywallet.ms.assets.ApplicationProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PublisherFactory {

    @Bean
    public KafkaPublisher kafkaPublisher(ApplicationProperties applicationProperties) {
        return new KafkaPublisher(producerConfigs(applicationProperties));
    }

    private Map<String, Object> producerConfigs(ApplicationProperties applicationProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getKafkaBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return props;
    }
}
