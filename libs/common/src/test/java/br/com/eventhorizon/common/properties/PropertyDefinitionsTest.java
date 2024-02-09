package br.com.eventhorizon.common.properties;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class PropertyDefinitionsTest {

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_1 = PropertyDefinition.of("property1", String.class, "default-value", "Property 1 description");

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_2 = PropertyDefinition.of("property2", Boolean.class, true, "Property 2 description");

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_3 = PropertyDefinition.of("property3", Long.class, 1L, "Property 3 description");

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_4 = PropertyDefinition.of("property4", Double.class, 1.0, "Property 4 description");

    @Test
    public void testDefineAndGet1() {
        var propertyDefinitions = new PropertyDefinitions()
                .add("property1", String.class, "default-value", "Property 1 description")
                .add("property2", Boolean.class, true, "Property 2 description")
                .add("property3", Long.class, 1L, "Property 3 description")
                .add("property4", Double.class, 1.0, "Property 4 description");

        propertyDefinitions.forEach(propertyDefinition -> log.info("{}", propertyDefinition));

        assertFalse(propertyDefinitions.contains("some-property"));
        assertTrue(propertyDefinitions.contains("property1"));
        assertTrue(propertyDefinitions.contains("property2"));
        assertTrue(propertyDefinitions.contains("property3"));
        assertTrue(propertyDefinitions.contains("property4"));

        assertThrows(PropertyDefinitionNotFoundException.class, () -> propertyDefinitions.get("some-property"));

        var propertyDefinition = propertyDefinitions.get("property1");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_1, propertyDefinition);

        propertyDefinition = propertyDefinitions.get("property2");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_2, propertyDefinition);

        propertyDefinition = propertyDefinitions.get("property3");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_3, propertyDefinition);

        propertyDefinition = propertyDefinitions.get("property4");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_4, propertyDefinition);
    }

    @Test
    public void testDefineAndGet2() {
        var propertyDefinitions = new PropertyDefinitions()
                .add(PROPERTY_DEFINITION_1)
                .add(PROPERTY_DEFINITION_2)
                .add(PROPERTY_DEFINITION_3)
                .add(PROPERTY_DEFINITION_4);

        propertyDefinitions.forEach(propertyDefinition -> log.info("{}", propertyDefinition));

        assertFalse(propertyDefinitions.contains("some-property"));
        assertTrue(propertyDefinitions.contains("property1"));
        assertTrue(propertyDefinitions.contains("property2"));
        assertTrue(propertyDefinitions.contains("property3"));
        assertTrue(propertyDefinitions.contains("property4"));

        assertThrows(PropertyDefinitionNotFoundException.class, () -> propertyDefinitions.get("some-property"));

        var propertyDefinition = propertyDefinitions.get("property1");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_1, propertyDefinition);

        propertyDefinition = propertyDefinitions.get("property2");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_2, propertyDefinition);

        propertyDefinition = propertyDefinitions.get("property3");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_3, propertyDefinition);

        propertyDefinition = propertyDefinitions.get("property4");
        assertNotNull(propertyDefinition);
        assertEquals(PROPERTY_DEFINITION_4, propertyDefinition);
    }

    @Test
    public void testDuplicateDefinition() {
        assertThrows(PropertyDefinitionAlreadyExistsException.class, () -> new PropertyDefinitions()
                .add(PROPERTY_DEFINITION_1)
                .add(PROPERTY_DEFINITION_1));
    }

    @Test
    public void testToCollection() {
        var propertyDefinitions = new PropertyDefinitions()
                .add(PROPERTY_DEFINITION_1)
                .add(PROPERTY_DEFINITION_2)
                .add(PROPERTY_DEFINITION_3)
                .add(PROPERTY_DEFINITION_4);

        var propertyDefinitionsCollection = propertyDefinitions.toCollection();

        assertNotNull(propertyDefinitionsCollection);
        assertEquals(4, propertyDefinitionsCollection.size());
        assertTrue(propertyDefinitionsCollection.contains(PROPERTY_DEFINITION_1));
        assertTrue(propertyDefinitionsCollection.contains(PROPERTY_DEFINITION_2));
        assertTrue(propertyDefinitionsCollection.contains(PROPERTY_DEFINITION_3));
        assertTrue(propertyDefinitionsCollection.contains(PROPERTY_DEFINITION_4));
    }
}
