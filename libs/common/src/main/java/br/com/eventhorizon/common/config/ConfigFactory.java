package br.com.eventhorizon.common.config;

import br.com.eventhorizon.common.config.impl.DefaultConfig;
import br.com.eventhorizon.common.config.impl.EnvVarsConfigSource;
import br.com.eventhorizon.common.config.impl.EnvironmentConfigSource;
import br.com.eventhorizon.common.config.impl.SystemPropertiesConfigSource;
import org.springframework.core.env.Environment;

public final class ConfigFactory {

    public static Config getDefaultConfig(Environment environment) {
        return DefaultConfig.builder()
                .source(new EnvVarsConfigSource())
                .source(new SystemPropertiesConfigSource())
                .source(new EnvironmentConfigSource(environment))
                .build();
    }
}
