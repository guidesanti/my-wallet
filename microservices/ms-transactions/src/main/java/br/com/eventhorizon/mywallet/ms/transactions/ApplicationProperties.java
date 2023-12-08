package br.com.eventhorizon.mywallet.ms.transactions;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = ApplicationProperties.PROPERTIES_PREFIX)
public final class ApplicationProperties {

    public static final String PROPERTIES_PREFIX = "br.com.eventhorizon.mywallet";

    public static final String BASE_PACKAGE = "br.com.eventhorizon.mywallet.ms.transactions";

    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".persistence.repository";

    public static final String START_DELAY_PROP_NAME =  PROPERTIES_PREFIX + ".start-delay";

    private Service service;

    private Kafka kafka;

    @Data
    public static final class Service {

        private String name;

        private String env;

        private int startDelay;
    }

    @Data
    public static final class Kafka {

        private String bootstrapServers;

        private Map<String, String> topics;
    }
}
