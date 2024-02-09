package br.com.eventhorizon.mywallet.ms.assets.persistence.model.mapper;

import br.com.eventhorizon.common.properties.PropertyDefinition;
import br.com.eventhorizon.common.properties.PropertyDefinitions;
import br.com.eventhorizon.mywallet.ms.assets.business.model.AssetType;
import br.com.eventhorizon.mywallet.ms.assets.business.model.enums.Income;
import br.com.eventhorizon.mywallet.ms.assets.business.model.enums.Strategy;
import br.com.eventhorizon.mywallet.ms.assets.persistence.model.AssetTypeDocument;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssetTypeDocumentMapperTest {

    private static final AssetTypeDocumentMapper MAPPER = AssetTypeDocumentMapper.INSTANCE;

    private static final PropertyDefinition<Boolean> PROP1 = PropertyDefinition.of("prop1", Boolean.class, false, "boolean");

    private static final PropertyDefinition<Long> PROP2 = PropertyDefinition.of("prop2", Long.class, 10L, "long");

    private static final PropertyDefinition<Double> PROP3 = PropertyDefinition.of("prop3", Double.class, 123.24, "double");

    private static final PropertyDefinition<String> PROP4 = PropertyDefinition.of("prop4", String.class, "value", "string");

    private static final AssetTypeDocument.PropertyDefinitionDocument<Boolean> PROP1_DOCUMENT = new AssetTypeDocument.PropertyDefinitionDocument<>("prop1", Boolean.class.getName(), false, "boolean");

    private static final AssetTypeDocument.PropertyDefinitionDocument<Long> PROP2_DOCUMENT = new AssetTypeDocument.PropertyDefinitionDocument<>("prop2", Long.class.getName(), 10L, "long");

    private static final AssetTypeDocument.PropertyDefinitionDocument<Double> PROP3_DOCUMENT = new AssetTypeDocument.PropertyDefinitionDocument<>("prop3", Double.class.getName(), 123.24, "double");

    private static final AssetTypeDocument.PropertyDefinitionDocument<String> PROP4_DOCUMENT = new AssetTypeDocument.PropertyDefinitionDocument<>("prop4", String.class.getName(), "value", "string");

    @Test
    void testToPersistence() {
        // Given
        var businessModel = AssetType.builder()
                .id("id")
                .name("name")
                .strategy(Strategy.NO_INCOME)
                .income(Income.NONE)
                .tradeableOnStockExchange(false)
                .description("description")
                .propertyDefinitions(new PropertyDefinitions()
                        .add("prop1", Boolean.class, false, "boolean")
                        .add("prop2", Long.class, 10L, "long")
                        .add("prop3", Double.class, 123.24, "double")
                        .add("prop4", String.class, "value", "string"))
                .build();

        // When
        var persistenceModel = MAPPER.toPersistence(businessModel);

        // Then
        assertNotNull(persistenceModel);
        assertEquals("id", persistenceModel.getId());
        assertEquals("name", persistenceModel.getName());
        assertEquals(Strategy.NO_INCOME.name(), persistenceModel.getStrategy());
        assertEquals(Income.NONE.name(), persistenceModel.getIncome());
        assertFalse(persistenceModel.isTradeableOnStockExchange());
        assertEquals("description", persistenceModel.getDescription());
        checkPropertyDefinitions(persistenceModel.getPropertyDefinitions());
    }

    @Test
    void testToBusiness() {
        // Given
        var persistenceModel = AssetTypeDocument.builder()
                .id("id")
                .name("name")
                .strategy("NO_INCOME")
                .income("NONE")
                .tradeableOnStockExchange(false)
                .description("description")
                .propertyDefinitions(List.of(PROP1_DOCUMENT, PROP2_DOCUMENT, PROP3_DOCUMENT, PROP4_DOCUMENT))
                .build();

        // When
        var businessModel = MAPPER.toBusiness(persistenceModel);

        // Then
        assertNotNull(businessModel);
        assertEquals("id", businessModel.getId());
        assertEquals("name", businessModel.getName());
        assertEquals(Strategy.NO_INCOME, businessModel.getStrategy());
        assertEquals(Income.NONE, businessModel.getIncome());
        assertFalse(businessModel.isTradeableOnStockExchange());
        assertEquals("description", businessModel.getDescription());
        checkPropertyDefinitions(businessModel.getPropertyDefinitions());
    }

    private void checkPropertyDefinitions(List<AssetTypeDocument.PropertyDefinitionDocument<?>> propertyDefinitions) {
        assertNotNull(propertyDefinitions);
        assertEquals(4, propertyDefinitions.size());
        assertTrue(propertyDefinitions.contains(PROP1_DOCUMENT));
        assertTrue(propertyDefinitions.contains(PROP2_DOCUMENT));
        assertTrue(propertyDefinitions.contains(PROP3_DOCUMENT));
        assertTrue(propertyDefinitions.contains(PROP4_DOCUMENT));
    }

    private void checkPropertyDefinitions(PropertyDefinitions propertyDefinitions) {
        assertNotNull(propertyDefinitions);

        assertTrue(propertyDefinitions.contains("prop1"));
        assertEquals(PROP1, propertyDefinitions.get("prop1"));

        assertTrue(propertyDefinitions.contains("prop2"));
        assertEquals(PROP2, propertyDefinitions.get("prop2"));

        assertTrue(propertyDefinitions.contains("prop3"));
        assertEquals(PROP3, propertyDefinitions.get("prop3"));

        assertTrue(propertyDefinitions.contains("prop4"));
        assertEquals(PROP4, propertyDefinitions.get("prop4"));
    }
}
