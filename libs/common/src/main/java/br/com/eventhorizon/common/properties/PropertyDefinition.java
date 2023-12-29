package br.com.eventhorizon.common.properties;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
public class PropertyDefinition<T> {

    private static final String NAME_PATTERN = "^[a-z0-9.]+$";

    private static final Set<Class<?>> SUPPORTED_TYPES = new HashSet<>();

    private final String name;

    private final Class<T> type;

    private final T defaultValue;

    private final String description;

    static {
        SUPPORTED_TYPES.add(String.class);
        SUPPORTED_TYPES.add(Boolean.class);
        SUPPORTED_TYPES.add(Long.class);
        SUPPORTED_TYPES.add(Double.class);
    }

    private PropertyDefinition(String name, Class<T> type, T defaultValue, String description) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.description = description;
        validateName(name);
        validateType(type);
        validateDefaultValue(defaultValue);
        validateDescription(description);
    }

    public static <T> PropertyDefinition<T> of(String name, Class<T> type, T defaultValue, String description) {
        return new PropertyDefinition<>(name, type, defaultValue, description);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || !name.matches(NAME_PATTERN)) {
            throw new InvalidPropertyDefinitionException(this, "Name cannot be null or blank and can only contains lower case letters, numbers and .");
        }
    }

    private void validateType(Class<T> type) {
        if (!SUPPORTED_TYPES.contains(type)) {
            throw new InvalidPropertyDefinitionException(this, "Unsupported type, supported types are " + SUPPORTED_TYPES);
        }
    }

    private void validateDefaultValue(T defaultValue) {
        if (defaultValue == null) {
            throw new InvalidPropertyDefinitionException(this, "Invalid default value, default value cannot be null");
        }
        if (defaultValue.getClass() != this.type) {
            throw new InvalidPropertyDefinitionException(this, "Invalid default value, default value type must match property definition type");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidPropertyDefinitionException(this, "Description cannot be null or blank");
        }
    }
}
