package br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.mapper;

import br.com.eventhorizon.common.properties.PropertyDefinition;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.AssetTypeDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface PropertyDefinitionDocumentMapper {

    PropertyDefinitionDocumentMapper INSTANCE = Mappers.getMapper(PropertyDefinitionDocumentMapper.class);

    @Mapping(target = "name", source = "business.name")
    @Mapping(target = "type", expression = "java(business.getType().getName())")
    @Mapping(target = "defaultValue", source = "business.defaultValue")
    @Mapping(target = "description", source = "business.description")
    AssetTypeDocument.PropertyDefinitionDocument toPersistence(PropertyDefinition business);

    @Mapping(target = "name", source = "document.name")
    @Mapping(target = "type", expression = "java(Class.forName(document.getType()))")
    @Mapping(target = "defaultValue", source = "document.defaultValue")
    @Mapping(target = "description", source = "document.description")
    PropertyDefinition toBusiness(AssetTypeDocument.PropertyDefinitionDocument document) throws ClassNotFoundException;
}
