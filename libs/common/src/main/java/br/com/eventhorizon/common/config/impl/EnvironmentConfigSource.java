package br.com.eventhorizon.common.config.impl;

import br.com.eventhorizon.common.config.ConfigSource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class EnvironmentConfigSource implements ConfigSource {

    private static final String NAME = "spring-environment-config-provider";

    private final Environment environment;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getValue(String name) {
        return environment.getProperty(name);
    }
}
