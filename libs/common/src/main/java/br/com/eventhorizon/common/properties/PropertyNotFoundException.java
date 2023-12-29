package br.com.eventhorizon.common.properties;

public class PropertyNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Property not found: %s";

    public PropertyNotFoundException(String propertyName) {
        super(String.format(MESSAGE_TEMPLATE, propertyName));
    }
}
