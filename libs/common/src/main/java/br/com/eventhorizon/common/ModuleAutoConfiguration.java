package br.com.eventhorizon.common;

import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.config.ConfigFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public Config config(Environment environment) {
        return ConfigFactory.getDefaultConfig(environment);
    }
}
