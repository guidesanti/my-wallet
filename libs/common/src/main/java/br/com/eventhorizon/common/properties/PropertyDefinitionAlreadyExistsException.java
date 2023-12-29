package br.com.eventhorizon.common.properties;

public class PropertyDefinitionAlreadyExistsException extends RuntimeException {

    public PropertyDefinitionAlreadyExistsException(String message) {
        super(message);
    }
}
