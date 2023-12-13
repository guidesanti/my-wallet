package br.com.eventhorizon.common.config;

import br.com.eventhorizon.common.config.impl.DefaultConfig;
import br.com.eventhorizon.common.config.impl.EnvVarsConfigProvider;
import br.com.eventhorizon.common.config.impl.EnvironmentConfigProvider;
import br.com.eventhorizon.common.config.impl.SystemPropertiesConfigProvider;
import org.springframework.core.env.Environment;

public final class ConfigFactory {

    public static Config getDefaultConfig(Environment environment) {
        return DefaultConfig.builder()
                .provider(new EnvVarsConfigProvider())
                .provider(new SystemPropertiesConfigProvider())
                .provider(new EnvironmentConfigProvider(environment))
                .build();
    }
}
