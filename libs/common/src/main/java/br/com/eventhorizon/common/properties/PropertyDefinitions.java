package br.com.eventhorizon.common.properties;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@EqualsAndHashCode
public final class PropertyDefinitions implements Iterable<PropertyDefinition<?>>, Serializable {

    @Serial
    private static final long serialVersionUID = -2945579297294218646L;

    private final Map<String, PropertyDefinition<?>> propertyDefinitions = new HashMap<>();

    public <T> PropertyDefinitions add(String name, Class<T> type, T defaultValue, String description) {
        return add(PropertyDefinition.of(name, type, defaultValue, description));
    }

    public PropertyDefinitions add(PropertyDefinition<?> propertyDefinition) {
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

    public Collection<PropertyDefinition<?>> toCollection() {
        return propertyDefinitions.values();
    }

    @Override
    public @NonNull Iterator<PropertyDefinition<?>> iterator() {
        return propertyDefinitions.values().iterator();
    }
}
