package br.com.eventhorizon.common.config.impl;

import br.com.eventhorizon.common.config.ConfigProvider;

public class SystemPropertiesConfigProvider implements ConfigProvider {

    private static final String NAME = "default-system-properties-config-provider";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getValue(String name) {
        return System.getProperty(name);
    }
}
