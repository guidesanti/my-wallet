package br.com.eventhorizon.common.config.impl;

import br.com.eventhorizon.common.config.ConfigProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnvVarsConfigProvider implements ConfigProvider {

    private static final String NAME = "default-env-vars-config-provider";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getValue(String name) {
        if (name == null) {
            return null;
        }
        return System.getenv(name);
    }
}
