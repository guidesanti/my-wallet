package br.com.eventhorizon.common.properties;

public class InvalidPropertyDefinitionException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "%s: %s";

    public InvalidPropertyDefinitionException(PropertyDefinition<?> propertyDefinition, String message) {
        super(String.format(MESSAGE_TEMPLATE, propertyDefinition, message));
    }
}
