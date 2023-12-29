package br.com.eventhorizon.common.properties;

public class InvalidPropertyValueException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Property definition type and provided type does not match, expected %s, but received %s";

    public InvalidPropertyValueException(Class<?> expectedType, Class<?> receivedType) {
        super(String.format(MESSAGE_TEMPLATE, expectedType, receivedType));
    }
}
