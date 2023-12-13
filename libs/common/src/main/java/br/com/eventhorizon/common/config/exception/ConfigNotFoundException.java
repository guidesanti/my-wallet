package br.com.eventhorizon.common.config.exception;

import lombok.Getter;

@Getter
public class ConfigNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Config with name '%s' not found";

    private final String configName;

    public ConfigNotFoundException(String configName) {
        super(String.format(MESSAGE_TEMPLATE, configName));
        this.configName = configName;
    }
}
