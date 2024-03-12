package br.com.eventhorizon.mywallet.ms.assets.infrastructure.messaging;

import br.com.eventhorizon.mywallet.ms.assets.ApplicationProperties;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.messaging.handler.GetAssetSagaSingleHandler;
import br.com.eventhorizon.mywallet.ms.assets.domain.entities.Asset;
import br.com.eventhorizon.saga.messaging.provider.kafka.KafkaSagaTransaction;
import br.com.eventhorizon.saga.messaging.publisher.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Subscriptions {

//    @Bean
//    public DefaultKafkaSingleSubscription<byte[]> getAssetKafkaSingleSubscription(
//            ApplicationProperties applicationProperties, GetAssetSingleMessageHandler handler) {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getKafkaBootstrapServers());
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, applicationProperties.getServiceName());
//
//        return DefaultKafkaSingleSubscription.<byte[]>builder()
//                .handler(handler)
//                .source(applicationProperties.getGetAssetKafkaTopicName())
//                .sourceType(byte[].class)
//                .config(config)
//                .build();
//    }

    @Bean
    public KafkaSagaTransaction<Asset, byte[]> getAssetSingleSubscription(
            ApplicationProperties applicationProperties,
            GetAssetSagaSingleHandler handler,
            SagaRepository sagaRepository,
            SagaPublisher sagaPublisher) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getKafkaBootstrapServers());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, applicationProperties.getServiceName());

        return KafkaSagaTransaction.<Asset, byte[]>builder()
                .id("get-asset-saga-transaction")
                .messagingProviderName("kafka")
                .source(applicationProperties.getGetAssetKafkaTopicName())
                .sourceType(byte[].class)
                .dlq(applicationProperties.getGetAssetDlqKafkaTopicName())
                .handler(handler)
                .repository(sagaRepository)
                .publisher(sagaPublisher)
                .kafkaConsumerConfig(config)
                .build();
    }
}
