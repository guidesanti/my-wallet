package br.com.eventhorizon.mywallet.ms.assets;

import br.com.eventhorizon.common.config.Config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public final class ApplicationProperties {

    public static final String PROPERTIES_PREFIX = "br.com.eventhorizon.mywallet";

    public static final String BASE_PACKAGE = "br.com.eventhorizon.mywallet.ms.assets";

    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".infrastructure.persistence.repository";

    public static final String START_DELAY_PROP_NAME =  PROPERTIES_PREFIX + ".start-delay";

    private final Config config;

    private final String serviceName;

    private final String kafkaBootstrapServers;

    private final String getAssetKafkaTopicName;

    private final String getAssetDlqKafkaTopicName;

    private final String assetCreatedKafkaTopicName;

    private final String assetUpdatedKafkaTopicName;

    @Autowired
    public ApplicationProperties(Config config) {
        this.config = config;
        this.serviceName = getPropertyValue("service.name", String.class);
        this.kafkaBootstrapServers = getPropertyValue("kafka.bootstrap-servers", String.class);
        this.getAssetKafkaTopicName = getPropertyValue("kafka.topics.assets-get-asset", String.class);
        this.getAssetDlqKafkaTopicName = getPropertyValue("kafka.topics.assets-get-asset-dlq", String.class);
        this.assetCreatedKafkaTopicName = getPropertyValue("kafka.topics.assets-asset-created", String.class);
        this.assetUpdatedKafkaTopicName = getPropertyValue("kafka.topics.assets-asset-updated", String.class);
    }

    private <T> T getPropertyValue(String name, Class<T> type) {
        return config.getValue(PROPERTIES_PREFIX + "." + name, type);
    }
}
