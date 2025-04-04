package br.com.eventhorizon.mywallet.ms.kafka.producer;

import br.com.eventhorizon.common.config.Config;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public final class ApplicationProperties {

    public static final String PROPERTIES_PREFIX = "br.com.eventhorizon.mywallet";

    public static final String BASE_PACKAGE = "br.com.eventhorizon.mywallet.ms.kafka.producer";

    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".infrastructure.persistence.repository";

    public static final String START_DELAY_PROP_NAME =  PROPERTIES_PREFIX + ".start-delay";

    private final Config config;

    private final String serviceName;

    private final String kafkaBootstrapServers;

    private final String topic1Name;

    private final String topic1DlqName;

    @Autowired
    public ApplicationProperties(Config config) {
        this.config = config;
        this.serviceName = getPropertyValue("service.name", String.class);
        this.kafkaBootstrapServers = getPropertyValue("kafka.bootstrap-servers", String.class);
        this.topic1Name = getPropertyValue("kafka.topics.topic1", String.class);
        this.topic1DlqName = getPropertyValue("kafka.topics.topic1-dlq", String.class);
    }

    private <T> T getPropertyValue(String name, Class<T> type) {
        return config.getValue(PROPERTIES_PREFIX + "." + name, type);
    }
}
