package br.com.eventhorizon.mywallet.ms.assets.infrastructure.http.model.mapper;

import br.com.eventhorizon.common.properties.PropertyDefinitions;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.AssetTypeDTO;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.AssetTypePropertyDefinition;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.CreateAssetTypeDTO;
import br.com.eventhorizon.mywallet.ms.assets.domain.entities.AssetType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AssetTypeDTOMapper {

    AssetTypeDTOMapper INSTANCE = Mappers.getMapper(AssetTypeDTOMapper.class);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "strategy", source = "dto.strategy")
    @Mapping(target = "income", source = "dto.income")
    @Mapping(target = "tradeableOnStockExchange", source = "dto.tradeableOnStockExchange")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "propertyDefinitions", source = "dto.propertyDefinitions", qualifiedByName = "toPropertyDefinitions")
    AssetType toBusiness(CreateAssetTypeDTO dto);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "strategy", source = "dto.strategy")
    @Mapping(target = "income", source = "dto.income")
    @Mapping(target = "tradeableOnStockExchange", source = "dto.tradeableOnStockExchange")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "propertyDefinitions", source = "dto.propertyDefinitions", qualifiedByName = "toPropertyDefinitions")
    AssetType toBusiness(AssetTypeDTO dto);

    @Mapping(target = "id", source = "assetType.id")
    @Mapping(target = "name", source = "assetType.name")
    @Mapping(target = "strategy", source = "assetType.strategy")
    @Mapping(target = "income", source = "assetType.income")
    @Mapping(target = "tradeableOnStockExchange", source = "assetType.tradeableOnStockExchange")
    @Mapping(target = "description", source = "assetType.description")
    @Mapping(target = "propertyDefinitions", source = "assetType.propertyDefinitions", qualifiedByName = "toPropertyDefinitionsDTO")
    AssetTypeDTO toDTO(AssetType assetType);

    @Named("toPropertyDefinitions")
    default PropertyDefinitions toPropertyDefinitions(List<AssetTypePropertyDefinition> dto) {
        var propertyDefinitions = new PropertyDefinitions();
        dto.forEach(assetTypePropertyDefinition -> propertyDefinitions.add(
                assetTypePropertyDefinition.getName(),
                convertType(assetTypePropertyDefinition.getType()),
                convertDefaultValue(assetTypePropertyDefinition.getDefaultValue(), assetTypePropertyDefinition.getType()),
                assetTypePropertyDefinition.getDescription()));
        return propertyDefinitions;
    }

    default Class convertType(AssetTypePropertyDefinition.TypeEnum type) {
        return switch (type) {
            case BOOLEAN -> Boolean.class;
            case LONG -> Long.class;
            case DOUBLE -> Double.class;
            case STRING -> String.class;
            default -> throw new RuntimeException();
        };
    }

    default Object convertDefaultValue(String value, AssetTypePropertyDefinition.TypeEnum type) {
        return switch (type) {
            case BOOLEAN -> Boolean.valueOf(value);
            case LONG -> Long.valueOf(value);
            case DOUBLE -> Double.valueOf(value);
            case STRING -> value;
            default -> throw new RuntimeException();
        };
    }

    @Named("toPropertyDefinitionsDTO")
    default List<AssetTypePropertyDefinition> toPropertyDefinitionsDTO(PropertyDefinitions propertyDefinitions) {
        var props = new ArrayList<AssetTypePropertyDefinition>();
        propertyDefinitions.forEach(propertyDefinition -> {
            props.add(new AssetTypePropertyDefinition(
                    propertyDefinition.getName(),
                    AssetTypePropertyDefinition.TypeEnum.valueOf(propertyDefinition.getType().getSimpleName().toUpperCase()),
                    String.valueOf(propertyDefinition.getDefaultValue()),
                    propertyDefinition.getDescription()
            ));
        });
        return props;
    }
}
