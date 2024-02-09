package br.com.eventhorizon.common.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyDefinitionTest {

    @Test
    public void testCreation() {
        // Invalid names
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of(null, String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("", String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of(" ", String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("$", String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("abc$", String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("*", String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("/", String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("PROP", String.class, "default-value", "description"));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("Prop1", String.class, "default-value", "description"));

        // Valid names
        assertEquals("property1", PropertyDefinition.of("property1", String.class, "default-value", "description").getName());
        assertEquals("property.property", PropertyDefinition.of("property.property", String.class, "default-value", "description").getName());
        assertEquals("property.property", PropertyDefinition.of("property.property", String.class, "default-value", "description").getName());

        // Invalid types
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("property", Byte.class, (byte) 1, "description"));

        // Valid types
        assertEquals(String.class, PropertyDefinition.of("property", String.class, "default-value", "description").getType());
        assertEquals(Boolean.class, PropertyDefinition.of("property", Boolean.class, true, "description").getType());
        assertEquals(Long.class, PropertyDefinition.of("property", Long.class, 1L, "description").getType());
        assertEquals(Double.class, PropertyDefinition.of("property", Double.class, 1.0, "description").getType());

        // Invalid default value
//        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("property", String.class, 2.0, "description"));

        // Valid default value
        assertEquals("default-value", PropertyDefinition.of("property", String.class, "default-value", "description").getDefaultValue());

        // Invalid description
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("property", String.class, "default-value", null));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("property", String.class, "default-value", ""));
        assertThrows(InvalidPropertyDefinitionException.class, () -> PropertyDefinition.of("property", String.class, "default-value", " "));

        // Valid description
        assertEquals("description", PropertyDefinition.of("property", String.class, "default-value", "description").getDescription());
    }
}
