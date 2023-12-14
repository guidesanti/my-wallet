package br.com.eventhorizon.common.config;

import br.com.eventhorizon.common.config.exception.ConfigNotFoundException;
import br.com.eventhorizon.common.config.impl.DefaultConfig;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultConfigTest {

    private final Config config = DefaultConfig.builder().source(new CustomConfigSource()).build();

    private static class CustomConfigSource implements ConfigSource {

        private static final String CUSTOM_CONFIG_SOURCE_NAME = "custom-config-source-name";

        private final Map<String, String> values = new HashMap<>();

        public CustomConfigSource() {
            values.put("boolean1", "TRUE");
            values.put("boolean2", "True");
            values.put("boolean3", "Yes");
            values.put("boolean4", "Y");
            values.put("boolean5", "On");
            values.put("boolean6", "1");
            values.put("boolean7", "FALSE");
            values.put("boolean8", "False");
            values.put("boolean9", "No");
            values.put("boolean10", "N");
            values.put("boolean11", "Off");
            values.put("boolean12", "0");
            values.put("char", "?");
            values.put("number", "96");
            values.put("decimal-number", "128.345");
            values.put("string", "some-string");
            values.put("class", "br.com.eventhorizon.common.config.impl.DefaultConfig");
            values.put("list-of-integers", "1,2,3,4,5");
        }

        @Override
        public String getName() {
            return CUSTOM_CONFIG_SOURCE_NAME;
        }

        @Override
        public String getValue(String name) {
            return values.get(name);
        }
    }

    @Test
    public void testGetValue() {
        assertEquals("TRUE", config.getValue("boolean1"));
        assertEquals("True", config.getValue("boolean2"));
        assertEquals("Yes", config.getValue("boolean3"));
        assertEquals("Y", config.getValue("boolean4"));
        assertEquals("On", config.getValue("boolean5"));
        assertEquals("1", config.getValue("boolean6"));
        assertEquals("FALSE", config.getValue("boolean7"));
        assertEquals("False", config.getValue("boolean8"));
        assertEquals("No", config.getValue("boolean9"));
        assertEquals("N", config.getValue("boolean10"));
        assertEquals("Off", config.getValue("boolean11"));
        assertEquals("0", config.getValue("boolean12"));
        assertEquals("?", config.getValue("char"));
        assertEquals("96", config.getValue("number"));
        assertEquals("128.345", config.getValue("decimal-number"));
        assertEquals("some-string", config.getValue("string"));
        assertEquals("br.com.eventhorizon.common.config.impl.DefaultConfig", config.getValue("class"));
        assertEquals("1,2,3,4,5", config.getValue("list-of-integers"));
        assertEquals(List.of("1", "2", "3", "4", "5"), config.getValues("list-of-integers"));
        assertThrows(ConfigNotFoundException.class, () -> config.getValue("non-existing-config-name"));
    }

    @Test
    public void testGetValueWithType() {
        assertEquals(Boolean.TRUE, config.getValue("boolean1", Boolean.class));
        assertEquals(Boolean.TRUE, config.getValue("boolean2", Boolean.class));
        assertEquals(Boolean.TRUE, config.getValue("boolean3", Boolean.class));
        assertEquals(Boolean.TRUE, config.getValue("boolean4", Boolean.class));
        assertEquals(Boolean.TRUE, config.getValue("boolean5", Boolean.class));
        assertEquals(Boolean.TRUE, config.getValue("boolean6", Boolean.class));
        assertEquals(Boolean.FALSE, config.getValue("boolean7", Boolean.class));
        assertEquals(Boolean.FALSE, config.getValue("boolean8", Boolean.class));
        assertEquals(Boolean.FALSE, config.getValue("boolean9", Boolean.class));
        assertEquals(Boolean.FALSE, config.getValue("boolean10", Boolean.class));
        assertEquals(Boolean.FALSE, config.getValue("boolean11", Boolean.class));
        assertEquals(Boolean.FALSE, config.getValue("boolean12", Boolean.class));

        assertEquals('?', config.getValue("char", Character.class));

        assertEquals((byte) 96, config.getValue("number", Byte.class));
        assertEquals(Byte.valueOf((byte) 96), config.getValue("number", Byte.class));
        assertEquals((short) 96, config.getValue("number", Short.class));
        assertEquals(Short.valueOf((short) 96), config.getValue("number", Short.class));
        assertEquals(96, config.getValue("number", Integer.class));
        assertEquals(Integer.valueOf(96), config.getValue("number", Integer.class));
        assertEquals(96L, config.getValue("number", Long.class));
        assertEquals(Long.valueOf(96L), config.getValue("number", Long.class));
        assertEquals(96.0f, config.getValue("number", Float.class));
        assertEquals(Float.valueOf(96.0f), config.getValue("number", Float.class));
        assertEquals(96.0, config.getValue("number", Double.class));
        assertEquals(Double.valueOf(96.0), config.getValue("number", Double.class));

        assertEquals(128.345f, config.getValue("decimal-number", Float.class));
        assertEquals(Float.valueOf(128.345f), config.getValue("decimal-number", Float.class));
        assertEquals(128.345, config.getValue("decimal-number", Double.class));
        assertEquals(Double.valueOf(128.345), config.getValue("decimal-number", Double.class));

        assertEquals("some-string", config.getValue("string", String.class));

        assertEquals(DefaultConfig.class, config.getValue("class", Class.class));

        assertEquals(List.of(1, 2, 3, 4, 5), config.getValues("list-of-integers", Integer.class));

        assertThrows(ConfigNotFoundException.class, () -> config.getValue("non-existing-config-name", Byte.class));
    }

    @Test
    public void testGetOptionalValue() {
        assertTrue(config.getOptionalValue("boolean1").isPresent());
        assertTrue(config.getOptionalValue("boolean2").isPresent());
        assertTrue(config.getOptionalValue("boolean3").isPresent());
        assertTrue(config.getOptionalValue("boolean4").isPresent());
        assertTrue(config.getOptionalValue("boolean5").isPresent());
        assertTrue(config.getOptionalValue("boolean6").isPresent());
        assertTrue(config.getOptionalValue("boolean7").isPresent());
        assertTrue(config.getOptionalValue("boolean8").isPresent());
        assertTrue(config.getOptionalValue("boolean9").isPresent());
        assertTrue(config.getOptionalValue("boolean10").isPresent());
        assertTrue(config.getOptionalValue("boolean11").isPresent());
        assertTrue(config.getOptionalValue("boolean12").isPresent());
        assertTrue(config.getOptionalValue("char").isPresent());
        assertTrue(config.getOptionalValue("number").isPresent());
        assertTrue(config.getOptionalValue("decimal-number").isPresent());
        assertTrue(config.getOptionalValue("string").isPresent());
        assertTrue(config.getOptionalValue("class").isPresent());
        assertTrue(config.getOptionalValue("list-of-integers").isPresent());
        assertTrue(config.getOptionalValues("list-of-integers").isPresent());
        assertFalse(config.getOptionalValue("non-existing-config-name").isPresent());

        assertEquals("TRUE", config.getOptionalValue("boolean1").get());
        assertEquals("True", config.getOptionalValue("boolean2").get());
        assertEquals("Yes", config.getOptionalValue("boolean3").get());
        assertEquals("Y", config.getOptionalValue("boolean4").get());
        assertEquals("On", config.getOptionalValue("boolean5").get());
        assertEquals("1", config.getOptionalValue("boolean6").get());
        assertEquals("FALSE", config.getOptionalValue("boolean7").get());
        assertEquals("False", config.getOptionalValue("boolean8").get());
        assertEquals("No", config.getOptionalValue("boolean9").get());
        assertEquals("N", config.getOptionalValue("boolean10").get());
        assertEquals("Off", config.getOptionalValue("boolean11").get());
        assertEquals("0", config.getOptionalValue("boolean12").get());
        assertEquals("?", config.getOptionalValue("char").get());
        assertEquals("96", config.getOptionalValue("number").get());
        assertEquals("128.345", config.getOptionalValue("decimal-number").get());
        assertEquals("some-string", config.getOptionalValue("string").get());
        assertEquals("br.com.eventhorizon.common.config.impl.DefaultConfig", config.getOptionalValue("class").get());
        assertEquals("1,2,3,4,5", config.getOptionalValue("list-of-integers").get());
        assertEquals(List.of("1", "2", "3", "4", "5"), config.getOptionalValues("list-of-integers").get());
    }

    @Test
    public void testGetOptionalValueWithType() {
        assertTrue(config.getOptionalValue("boolean1", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean2", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean3", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean4", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean5", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean6", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean7", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean8", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean9", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean10", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean11", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("boolean12", Boolean.class).isPresent());
        assertTrue(config.getOptionalValue("char", Character.class).isPresent());
        assertTrue(config.getOptionalValue("number", Byte.class).isPresent());
        assertTrue(config.getOptionalValue("number", Short.class).isPresent());
        assertTrue(config.getOptionalValue("number", Integer.class).isPresent());
        assertTrue(config.getOptionalValue("number", Long.class).isPresent());
        assertTrue(config.getOptionalValue("number", Float.class).isPresent());
        assertTrue(config.getOptionalValue("number", Double.class).isPresent());
        assertTrue(config.getOptionalValue("decimal-number", Float.class).isPresent());
        assertTrue(config.getOptionalValue("decimal-number", Double.class).isPresent());
        assertTrue(config.getOptionalValue("string", String.class).isPresent());
        assertTrue(config.getOptionalValue("class", Class.class).isPresent());
        assertTrue(config.getOptionalValues("list-of-integers", Integer.class).isPresent());
        assertFalse(config.getOptionalValue("non-existing-config-name", Byte.class).isPresent());

        assertEquals(Boolean.TRUE, config.getOptionalValue("boolean1", Boolean.class).get());
        assertEquals(Boolean.TRUE, config.getOptionalValue("boolean2", Boolean.class).get());
        assertEquals(Boolean.TRUE, config.getOptionalValue("boolean3", Boolean.class).get());
        assertEquals(Boolean.TRUE, config.getOptionalValue("boolean4", Boolean.class).get());
        assertEquals(Boolean.TRUE, config.getOptionalValue("boolean5", Boolean.class).get());
        assertEquals(Boolean.TRUE, config.getOptionalValue("boolean6", Boolean.class).get());
        assertEquals(Boolean.FALSE, config.getOptionalValue("boolean7", Boolean.class).get());
        assertEquals(Boolean.FALSE, config.getOptionalValue("boolean8", Boolean.class).get());
        assertEquals(Boolean.FALSE, config.getOptionalValue("boolean9", Boolean.class).get());
        assertEquals(Boolean.FALSE, config.getOptionalValue("boolean10", Boolean.class).get());
        assertEquals(Boolean.FALSE, config.getOptionalValue("boolean11", Boolean.class).get());
        assertEquals(Boolean.FALSE, config.getOptionalValue("boolean12", Boolean.class).get());

        assertEquals('?', config.getOptionalValue("char", Character.class).get());

        assertEquals((byte) 96, config.getOptionalValue("number", Byte.class).get());
        assertEquals(Byte.valueOf((byte) 96), config.getOptionalValue("number", Byte.class).get());
        assertEquals((short) 96, config.getOptionalValue("number", Short.class).get());
        assertEquals(Short.valueOf((short) 96), config.getOptionalValue("number", Short.class).get());
        assertEquals(96, config.getOptionalValue("number", Integer.class).get());
        assertEquals(Integer.valueOf(96), config.getOptionalValue("number", Integer.class).get());
        assertEquals(96L, config.getOptionalValue("number", Long.class).get());
        assertEquals(Long.valueOf(96L), config.getOptionalValue("number", Long.class).get());
        assertEquals(96.0f, config.getOptionalValue("number", Float.class).get());
        assertEquals(Float.valueOf(96.0f), config.getOptionalValue("number", Float.class).get());
        assertEquals(96.0, config.getOptionalValue("number", Double.class).get());
        assertEquals(Double.valueOf(96.0), config.getOptionalValue("number", Double.class).get());

        assertEquals(128.345f, config.getOptionalValue("decimal-number", Float.class).get());
        assertEquals(Float.valueOf(128.345f), config.getOptionalValue("decimal-number", Float.class).get());
        assertEquals(128.345, config.getOptionalValue("decimal-number", Double.class).get());
        assertEquals(Double.valueOf(128.345), config.getOptionalValue("decimal-number", Double.class).get());

        assertEquals("some-string", config.getOptionalValue("string", String.class).get());

        assertEquals(DefaultConfig.class, config.getOptionalValue("class", Class.class).get());

        assertEquals(List.of(1, 2, 3, 4, 5), config.getOptionalValues("list-of-integers", Integer.class).get());
    }
}
