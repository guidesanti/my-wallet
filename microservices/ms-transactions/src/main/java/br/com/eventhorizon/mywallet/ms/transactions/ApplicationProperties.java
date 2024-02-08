package br.com.eventhorizon.mywallet.ms.transactions;

import br.com.eventhorizon.common.config.Config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public final class ApplicationProperties {

    public static final String PROPERTIES_PREFIX = "br.com.eventhorizon.mywallet";

    public static final String BASE_PACKAGE = "br.com.eventhorizon.mywallet.ms.transactions";

    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".persistence.repository";

    public static final String START_DELAY_PROP_NAME =  PROPERTIES_PREFIX + ".start-delay";

    private final Config config;

    private final String serviceName;

    private final String kafkaBootstrapServers;

    private final String transactionCreatedKafkaTopicName;

    private final String getAssetKafkaTopicName;

    @Autowired
    public ApplicationProperties(Config config) {
        this.config = config;
        this.serviceName = getPropertyValue("service.name", String.class);
        this.kafkaBootstrapServers = getPropertyValue("kafka.bootstrap-servers", String.class);
        this.transactionCreatedKafkaTopicName = getPropertyValue("kafka.topics.transactions-transaction-created", String.class);
        this.getAssetKafkaTopicName = getPropertyValue("kafka.topics.assets-get-asset", String.class);
    }

    private <T> T getPropertyValue(String name, Class<T> type) {
        return config.getValue(PROPERTIES_PREFIX + "." + name, type);
    }
}
