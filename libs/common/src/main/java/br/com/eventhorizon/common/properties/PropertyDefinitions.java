package br.com.eventhorizon.common.properties;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@EqualsAndHashCode
public final class PropertyDefinitions implements Iterable<PropertyDefinition<?>> {

    private final Map<String, PropertyDefinition<?>> propertyDefinitions = new HashMap<>();

    public <T> PropertyDefinitions define(String name, Class<T> type, T defaultValue, String description) {
        return define(PropertyDefinition.of(name, type, defaultValue, description));
    }

    public PropertyDefinitions define(PropertyDefinition<?> propertyDefinition) {
        if (propertyDefinitions.containsKey(propertyDefinition.getName())) {
            throw new PropertyDefinitionAlreadyExistsException("Property definition already exists" + propertyDefinition);
        }
        propertyDefinitions.put(propertyDefinition.getName(), propertyDefinition);
        return this;
    }

    public PropertyDefinition<?> get(String propertyName) {
        var propertyDefinition = propertyDefinitions.get(propertyName);
        if (propertyDefinition != null) {
            return propertyDefinition;
        }
        throw new PropertyDefinitionNotFoundException(propertyName);
    }

    public boolean contains(String propertyName) {
        return propertyDefinitions.containsKey(propertyName);
    }

    @Override
    public @NonNull Iterator<PropertyDefinition<?>> iterator() {
        return propertyDefinitions.values().iterator();
    }
}
