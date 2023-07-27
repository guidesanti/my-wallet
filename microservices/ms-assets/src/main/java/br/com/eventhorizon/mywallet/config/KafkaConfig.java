package br.com.eventhorizon.mywallet.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${br.com.eventhorizon.my-wallet.kafka.topic.asset-management}")
    private String assetManagementKafkaTopicName;

    @Bean
    public NewTopic kafkaTopicTest() {
        return TopicBuilder
                .name("test")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic kafkaTopicAssetManagement() {
        return TopicBuilder
                .name(assetManagementKafkaTopicName)
                .partitions(3)
                .replicas(3)
                .build();
    }
}
