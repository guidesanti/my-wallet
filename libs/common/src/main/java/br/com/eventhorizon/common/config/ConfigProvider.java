package br.com.eventhorizon.common.config;

import br.com.eventhorizon.common.config.impl.DefaultConfig;
import br.com.eventhorizon.common.config.impl.EnvVarsConfigSource;
import br.com.eventhorizon.common.config.impl.EnvironmentConfigSource;
import br.com.eventhorizon.common.config.impl.SystemPropertiesConfigSource;
import lombok.Builder;
import org.springframework.core.env.Environment;

@Builder
public final class ConfigProvider {

    public static Config getConfig(Environment environment) {
        return DefaultConfig.builder()
                .source(new EnvVarsConfigSource())
                .source(new SystemPropertiesConfigSource())
                .source(new EnvironmentConfigSource(environment))
                .build();
    }
}
