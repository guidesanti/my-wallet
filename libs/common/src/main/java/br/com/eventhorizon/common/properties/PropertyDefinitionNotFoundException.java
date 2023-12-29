package br.com.eventhorizon.common.properties;

public class PropertyDefinitionNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Property definition not found: %s";

    public PropertyDefinitionNotFoundException(String propertyName) {
        super(String.format(MESSAGE_TEMPLATE, propertyName));
    }
}
