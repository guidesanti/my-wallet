package br.com.eventhorizon.mywallet.ms.assets.infrastructure.messaging;

import br.com.eventhorizon.messaging.provider.kafka.subscription.KafkaSingleSubscription;
import br.com.eventhorizon.messaging.provider.publisher.Publisher;
import br.com.eventhorizon.messaging.provider.subscriber.chain.filter.OnErrorPublishToDestinationMessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.chain.filter.OnErrorPublishToSourceMessageFilter;
import br.com.eventhorizon.mywallet.ms.assets.ApplicationProperties;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.messaging.handler.GetAssetSingleMessageHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SubscriptionFactory {

    @Bean
    public KafkaSingleSubscription<byte[]> getAssetKafkaSingleSubscription(
            ApplicationProperties applicationProperties,
            GetAssetSingleMessageHandler handler,
            Publisher publisher) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getKafkaBootstrapServers());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, applicationProperties.getServiceName());

        // TODO: Remove this later
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);

        return KafkaSingleSubscription.<byte[]>builder()
                .id("get-asset-kafka-subscription")
                .filter(OnErrorPublishToDestinationMessageFilter.<byte[]>builder()
                        .config(applicationProperties.getConfig())
                        .destination(applicationProperties.getGetAssetDlqKafkaTopicName())
                        .publisher(publisher)
                        .build())
                .filter(OnErrorPublishToSourceMessageFilter.<byte[]>builder()
                        .config(applicationProperties.getConfig())
                        .publisher(publisher)
                        .maxRetries(3)
                        .build())
                .handler(handler)
                .source(applicationProperties.getGetAssetKafkaTopicName())
                .sourceType(byte[].class)
                .configs(config)
                .build();
    }

//    @Bean
//    public KafkaSagaTransaction<Asset, byte[]> getAssetSingleSubscription(
//            ApplicationProperties applicationProperties,
//            GetAssetSagaSingleHandler handler,
//            SagaRepository sagaRepository,
//            SagaPublisher sagaPublisher) {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getKafkaBootstrapServers());
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, applicationProperties.getServiceName());
//
//        return KafkaSagaTransaction.<Asset, byte[]>builder()
//                .id("get-asset-saga-transaction")
//                .messagingProviderName("kafka")
//                .source(applicationProperties.getGetAssetKafkaTopicName())
//                .sourceType(byte[].class)
//                .dlq(applicationProperties.getGetAssetDlqKafkaTopicName())
//                .handler(handler)
//                .repository(sagaRepository)
//                .publisher(sagaPublisher)
//                .kafkaConsumerConfig(config)
//                .build();
//    }
}
