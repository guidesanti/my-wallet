package br.com.eventhorizon.common.config.impl;

import br.com.eventhorizon.common.config.ConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class EnvironmentConfigProvider implements ConfigProvider {

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
