package br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.mapper;

import br.com.eventhorizon.common.properties.PropertyDefinitions;
import br.com.eventhorizon.common.repository.PersistenceModelMappingException;
import br.com.eventhorizon.mywallet.ms.assets.domain.entities.AssetType;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.AssetTypeDocument;
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
public interface AssetTypeDocumentMapper {

    AssetTypeDocumentMapper INSTANCE = Mappers.getMapper(AssetTypeDocumentMapper.class);

    @Mapping(target = "name", source = "business.name")
    @Mapping(target = "strategy", source = "business.strategy")
    @Mapping(target = "income", source = "business.income")
    @Mapping(target = "tradeableOnStockExchange", source = "business.tradeableOnStockExchange")
    @Mapping(target = "description", source = "business.description")
    @Mapping(target = "propertyDefinitions", source = "business.propertyDefinitions", qualifiedByName = "toPropertyDefinitionsDocument")
    AssetTypeDocument toPersistence(AssetType business);

    @Mapping(target = "name", source = "document.name")
    @Mapping(target = "strategy", source = "document.strategy")
    @Mapping(target = "income", source = "document.income")
    @Mapping(target = "tradeableOnStockExchange", source = "document.tradeableOnStockExchange")
    @Mapping(target = "description", source = "document.description")
    @Mapping(target = "propertyDefinitions", source = "document.propertyDefinitions", qualifiedByName = "toPropertyDefinitions")
    AssetType toBusiness(AssetTypeDocument document);

    @Named("toPropertyDefinitions")
    default PropertyDefinitions toPropertyDefinitions(List<AssetTypeDocument.PropertyDefinitionDocument<?>> propertyDefinitionDocuments) {
        var propertyDefinitions = new PropertyDefinitions();
        propertyDefinitionDocuments.forEach(propertyDefinitionDocument -> {
            try {
                propertyDefinitions.add(PropertyDefinitionDocumentMapper.INSTANCE.toBusiness(propertyDefinitionDocument));
            } catch (ClassNotFoundException e) {
                throw new PersistenceModelMappingException(String.format("PropertyDefinition mapping failed, cannot map from '%s'", propertyDefinitionDocument.getType()), e);
            }
        });
        return propertyDefinitions;
    }

    @Named("toPropertyDefinitionsDocument")
    default List<AssetTypeDocument.PropertyDefinitionDocument<?>> toPropertyDefinitionsDocument(PropertyDefinitions propertyDefinitions) {
        var propertyDefinitionDocuments = new ArrayList<AssetTypeDocument.PropertyDefinitionDocument<?>>();
        propertyDefinitions.forEach(propertyDefinition -> propertyDefinitionDocuments.add(PropertyDefinitionDocumentMapper.INSTANCE.toPersistence(propertyDefinition)));
        return propertyDefinitionDocuments;
    }
}
