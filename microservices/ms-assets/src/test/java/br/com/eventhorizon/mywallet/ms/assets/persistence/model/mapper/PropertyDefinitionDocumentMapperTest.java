package br.com.eventhorizon.mywallet.ms.assets.persistence.model.mapper;

import br.com.eventhorizon.common.properties.PropertyDefinition;
import br.com.eventhorizon.mywallet.ms.assets.persistence.model.AssetTypeDocument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PropertyDefinitionDocumentMapperTest {

    private static final PropertyDefinitionDocumentMapper MAPPER = PropertyDefinitionDocumentMapper.INSTANCE;

    @Test
    void testToPersistence() {
        // Given
        var businessModel = PropertyDefinition.of("property.name", String.class, "default-value", "description");

        // When
        var persistenceModel = MAPPER.toPersistence(businessModel);

        // Then
        assertNotNull(persistenceModel);
        assertEquals("property.name", persistenceModel.getName());
        assertEquals(String.class.getName(), persistenceModel.getType());
        assertEquals("default-value", persistenceModel.getDefaultValue());
        assertEquals("description", persistenceModel.getDescription());
    }

    @Test
    void testToBusiness() throws ClassNotFoundException {
        // Given
        var persistenceModel = AssetTypeDocument.PropertyDefinitionDocument.builder()
                .name("property.name")
                .type(String.class.getName())
                .defaultValue("default-value")
                .description("description")
                .build();

        // When
        var businessModel = MAPPER.toBusiness(persistenceModel);

        // Then
        assertNotNull(businessModel);
        assertEquals("property.name", businessModel.getName());
        assertEquals(String.class, businessModel.getType());
        assertEquals("default-value", businessModel.getDefaultValue());
        assertEquals("description", businessModel.getDescription());
    }
}
