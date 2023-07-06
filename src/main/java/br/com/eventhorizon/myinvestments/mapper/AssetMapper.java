package br.com.eventhorizon.myinvestments.mapper;

import br.com.eventhorizon.myinvestments.dto.AssetDTO;
import br.com.eventhorizon.myinvestments.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface AssetMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "strategy", target = "strategy")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "properties", target = "properties")
    Asset toModel(AssetDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "strategy", target = "strategy")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "properties", target = "properties")
    AssetDTO toDto(Asset model);
}
