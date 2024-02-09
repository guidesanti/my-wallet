package br.com.eventhorizon.mywallet.ms.assets.api.http.model.mapper;

import br.com.eventhorizon.common.properties.PropertyDefinitions;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.*;
import br.com.eventhorizon.mywallet.ms.assets.business.model.AssetType;
import br.com.eventhorizon.mywallet.ms.assets.business.model.enums.Income;
import br.com.eventhorizon.mywallet.ms.assets.business.model.enums.Strategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssetTypeDTOMapperTest {

    private static final AssetTypePropertyDefinition PROP1 = new AssetTypePropertyDefinition("prop1", AssetTypePropertyDefinition.TypeEnum.BOOLEAN, "false", "boolean");

    private static final AssetTypePropertyDefinition PROP2 = new AssetTypePropertyDefinition("prop2", AssetTypePropertyDefinition.TypeEnum.LONG, "10", "long");

    private static final AssetTypePropertyDefinition PROP3 = new AssetTypePropertyDefinition("prop3", AssetTypePropertyDefinition.TypeEnum.DOUBLE, "123.24", "double");

    private static final AssetTypePropertyDefinition PROP4 = new AssetTypePropertyDefinition("prop4", AssetTypePropertyDefinition.TypeEnum.STRING, "value", "string");

    private static final List<AssetTypePropertyDefinition> PROPS = List.of(PROP1, PROP2, PROP3, PROP4);

    @Test
    void testCreateAssetTypeDTOToDomain() {
        // Given
        var dto = new CreateAssetTypeDTO("name", AssetTypeStrategy.NO_INCOME, AssetTypeIncome.NONE, false, "description");
        dto.setPropertyDefinitions(PROPS);

        // When
        var businessModel = AssetTypeDTOMapper.INSTANCE.toBusiness(dto);

        // Then
        assertNotNull(businessModel);
        assertNull(businessModel.getId());
        assertEquals("name", businessModel.getName());
        assertEquals(Strategy.NO_INCOME, businessModel.getStrategy());
        assertEquals(Income.NONE, businessModel.getIncome());
        assertFalse(businessModel.isTradeableOnStockExchange());
        assertEquals("description", businessModel.getDescription());
        checkPropertyDefinitions(businessModel.getPropertyDefinitions());
    }

    @Test
    void testAssetTypeDTOToDomain() {
        // Given
        var dto = new AssetTypeDTO("id", "name", AssetTypeStrategy.NO_INCOME, AssetTypeIncome.NONE, false, "description");
        dto.setPropertyDefinitions(PROPS);

        // When
        var businessModel = AssetTypeDTOMapper.INSTANCE.toBusiness(dto);

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

    @Test
    void testAssetTypeToDTO() {
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
        var dto = AssetTypeDTOMapper.INSTANCE.toDTO(businessModel);

        // Then
        assertNotNull(dto);
        assertEquals("id", dto.getId());
        assertEquals("name", dto.getName());
        assertEquals(AssetTypeStrategy.NO_INCOME, dto.getStrategy());
        assertEquals(AssetTypeIncome.NONE, dto.getIncome());
        assertFalse(dto.getTradeableOnStockExchange());
        assertEquals("description", dto.getDescription());
        checkPropertyDefinitions(dto.getPropertyDefinitions());
    }

    private void checkPropertyDefinitions(PropertyDefinitions propertyDefinitions) {
        assertNotNull(propertyDefinitions);

        assertTrue(propertyDefinitions.contains("prop1"));
        var propertyDefinition = propertyDefinitions.get("prop1");
        assertEquals("prop1", propertyDefinition.getName());
        assertEquals(Boolean.class, propertyDefinition.getType());
        assertEquals(false, propertyDefinition.getDefaultValue());
        assertEquals("boolean", propertyDefinition.getDescription());

        assertTrue(propertyDefinitions.contains("prop2"));
        propertyDefinition = propertyDefinitions.get("prop2");
        assertEquals("prop2", propertyDefinition.getName());
        assertEquals(Long.class, propertyDefinition.getType());
        assertEquals(10L, propertyDefinition.getDefaultValue());
        assertEquals("long", propertyDefinition.getDescription());

        assertTrue(propertyDefinitions.contains("prop3"));
        propertyDefinition = propertyDefinitions.get("prop3");
        assertEquals("prop3", propertyDefinition.getName());
        assertEquals(Double.class, propertyDefinition.getType());
        assertEquals(123.24, propertyDefinition.getDefaultValue());
        assertEquals("double", propertyDefinition.getDescription());

        assertTrue(propertyDefinitions.contains("prop4"));
        propertyDefinition = propertyDefinitions.get("prop4");
        assertEquals("prop4", propertyDefinition.getName());
        assertEquals(String.class, propertyDefinition.getType());
        assertEquals("value", propertyDefinition.getDefaultValue());
        assertEquals("string", propertyDefinition.getDescription());
    }

    private void checkPropertyDefinitions(List<AssetTypePropertyDefinition> propertyDefinitions) {
        assertNotNull(propertyDefinitions);
        assertEquals(4, propertyDefinitions.size());
        assertTrue(propertyDefinitions.contains(PROP1));
        assertTrue(propertyDefinitions.contains(PROP2));
        assertTrue(propertyDefinitions.contains(PROP3));
        assertTrue(propertyDefinitions.contains(PROP4));
    }
}
