package br.com.eventhorizon.mywallet.ms.kafka.consumer;

import br.com.eventhorizon.common.config.Config;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public final class ApplicationProperties {

    public static final String PROPERTIES_PREFIX = "br.com.eventhorizon";

    public static final String BASE_PACKAGE = "br.com.eventhorizon.mywallet.ms.kafka.producer";

    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".infrastructure.persistence.repository";

    public static final String START_DELAY_PROP_NAME =  PROPERTIES_PREFIX + ".start-delay";

    private final Config config;

    private final String serviceName;

    private final String kafkaBootstrapServers;

    private final Map<KafkaTopic, KafkaTopicProperties> kafkaTopics = new HashMap<>();

    @Autowired
    public ApplicationProperties(Config config) {
        this.config = config;
        this.serviceName = getPropertyValue("service.name", String.class);
        this.kafkaBootstrapServers = getPropertyValue("kafka.bootstrap-servers", String.class);
        loadKafkaTopicsProperties();
    }

    private <T> T getPropertyValue(String name, Class<T> type) {
        return config.getValue(PROPERTIES_PREFIX + "." + name, type);
    }

    private void loadKafkaTopicsProperties() {
        kafkaTopics.replaceAll((t, v) -> KafkaTopicProperties.builder()
                .name(getPropertyValue(t.getPropName(), String.class))
                .build());
    }

    @Builder
    public record KafkaTopicProperties(String name) {
    }

    @Getter
    @RequiredArgsConstructor
    public enum KafkaTopic {

        TEST("test"),
        TEST_DLQ("test-dlq");

        private static final String PROP_PREFIX = "kafka.topics";

        private final String propName;
    }
}
