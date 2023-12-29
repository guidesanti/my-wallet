package br.com.eventhorizon.common.properties;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesTest {

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_1 = PropertyDefinition.of(CustomProperties.PROPERTY1, String.class, "default-value", "Property 1 description");

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_2 = PropertyDefinition.of(CustomProperties.PROPERTY2, Boolean.class, true, "Property 2 description");

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_3 = PropertyDefinition.of(CustomProperties.PROPERTY3, Long.class, 1L, "Property 3 description");

    private static final PropertyDefinition<?> PROPERTY_DEFINITION_4 = PropertyDefinition.of(CustomProperties.PROPERTY4, Double.class, 1.0, "Property 4 description");

    @Test
    public void testDefaultValues() {
        var properties = new CustomProperties();
        assertEquals(PROPERTY_DEFINITION_1.getDefaultValue(), properties.getPropertyValue(PROPERTY_DEFINITION_1.getName()));
        assertEquals(PROPERTY_DEFINITION_2.getDefaultValue(), properties.getPropertyValue(PROPERTY_DEFINITION_2.getName()));
        assertEquals(PROPERTY_DEFINITION_3.getDefaultValue(), properties.getPropertyValue(PROPERTY_DEFINITION_3.getName()));
        assertEquals(PROPERTY_DEFINITION_4.getDefaultValue(), properties.getPropertyValue(PROPERTY_DEFINITION_4.getName()));
    }

    @Test
    public void testCustomValues() {
        var propertiesMap = new HashMap<String, Object>();
        propertiesMap.put("property1", "custom-value");
        propertiesMap.put("property2", false);
        propertiesMap.put("property3", 2L);
        propertiesMap.put("property4", 2.0);

        var properties = new CustomProperties(propertiesMap);
        assertEquals("custom-value", properties.getPropertyValue(PROPERTY_DEFINITION_1.getName()));
        assertEquals(false, properties.getPropertyValue(PROPERTY_DEFINITION_2.getName()));
        assertEquals(Long.valueOf(2L), properties.getPropertyValue(PROPERTY_DEFINITION_3.getName()));
        assertEquals(Double.valueOf(2.0), properties.getPropertyValue(PROPERTY_DEFINITION_4.getName()));
    }

    @Test
    public void testInvalidValues() {
        assertThrows(InvalidPropertyValueException.class, () -> new CustomProperties(Map.of("property1", 1L)));
        assertThrows(InvalidPropertyValueException.class, () -> new CustomProperties(Map.of("property2", "value")));
        assertThrows(InvalidPropertyValueException.class, () -> new CustomProperties(Map.of("property3", 1.0)));
        assertThrows(InvalidPropertyValueException.class, () -> new CustomProperties(Map.of("property4", "value")));
    }

    @Test
    public void testPropertyNotFound() {
        var properties = new CustomProperties();
        assertThrows(PropertyNotFoundException.class, () -> properties.getPropertyValue("some-property1"));
        assertThrows(PropertyNotFoundException.class, () -> properties.getPropertyValue("some-property2"));
    }

    @Test
    public void testGetPropertyDefinitions() {
        var properties = new CustomProperties();
        assertEquals(CustomProperties.PROPERTY_DEFINITIONS, properties.getPropertyDefinitions());
    }

    private static class CustomProperties extends Properties {

        private static final PropertyDefinitions PROPERTY_DEFINITIONS;

        public static final String PROPERTY1 = "property1";

        public static final String PROPERTY2 = "property2";

        public static final String PROPERTY3 = "property3";

        public static final String PROPERTY4 = "property4";

        static {
            PROPERTY_DEFINITIONS = new PropertyDefinitions()
                    .define(PROPERTY_DEFINITION_1)
                    .define(PROPERTY_DEFINITION_2)
                    .define(PROPERTY_DEFINITION_3)
                    .define(PROPERTY_DEFINITION_4);
        }

        public CustomProperties() {
            super(PROPERTY_DEFINITIONS, Collections.emptyMap());
        }

        public CustomProperties(Map<String, Object> properties) {
            super(PROPERTY_DEFINITIONS, properties);
        }
    }
}
