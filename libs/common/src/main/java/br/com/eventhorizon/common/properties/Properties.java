package br.com.eventhorizon.common.properties;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public abstract class Properties {

    @Getter
    private final PropertyDefinitions propertyDefinitions;

    protected final Map<String, Object> properties = new HashMap<>();

    protected Properties(PropertyDefinitions propertyDefinitions, Map<String, Object> properties) {
        this.propertyDefinitions = propertyDefinitions;
        propertyDefinitions.forEach(propertyDefinition -> this.properties.put(propertyDefinition.getName(), propertyDefinition.getDefaultValue()));
        properties.forEach(this::setPropertyValue);
    }

    public <T> T getPropertyValue(String propertyName) {
        var value = properties.get(propertyName);
        if (value != null) {
            return (T) value;
        }
        throw new PropertyNotFoundException(propertyName);
    }

    public <T> void setPropertyValue(String propertyName, T propertyValue) {
        validateProperty(propertyName, propertyValue);
        this.properties.put(propertyName, propertyValue);
    }

    protected <T> void validateProperty(String propertyName, T propertyValue) {
        var propertyDefinition = propertyDefinitions.get(propertyName);
        if (!propertyValue.getClass().equals(propertyDefinition.getType())) {
            throw new InvalidPropertyValueException(propertyDefinition.getType(), propertyValue.getClass());
        }
    }
}
