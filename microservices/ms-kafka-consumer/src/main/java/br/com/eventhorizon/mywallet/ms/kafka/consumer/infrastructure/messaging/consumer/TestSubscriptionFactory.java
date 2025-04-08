package br.com.eventhorizon.mywallet.ms.kafka.consumer.infrastructure.messaging.consumer;

import br.com.eventhorizon.messaging.provider.kafka.subscription.KafkaSingleSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.SubscriptionProperties;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TestSubscriptionFactory {

    @Bean
    public Subscription<byte[]> testSubscription(ApplicationProperties applicationProperties, TestHandler handler) {
        return KafkaSingleSubscription.<byte[]>builder()
                .id("test-kafka-subscription")
                .source("test")
                .handler(handler)
                .sourceType(byte[].class)
                .properties(properties())
                .configs(configs(applicationProperties))
                .build();
    }

    private SubscriptionProperties properties() {
        var props = new SubscriptionProperties();
        props.setPropertyValue(SubscriptionProperties.CONCURRENCY, 1L);
        return props;
    }

    private Map<String, Object> configs(ApplicationProperties applicationProperties) {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getKafkaBootstrapServers());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, applicationProperties.getServiceName());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return configs;
    }
}
