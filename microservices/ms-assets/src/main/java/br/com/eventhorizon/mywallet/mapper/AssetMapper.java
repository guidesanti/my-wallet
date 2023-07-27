package br.com.eventhorizon.mywallet.mapper;

import br.com.eventhorizon.mywallet.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface AssetMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "shortName", target = "shortName")
    @Mapping(source = "longName", target = "longName")
    @Mapping(source = "strategy", target = "strategy")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "properties", target = "properties")
    Asset toModel(br.com.eventhorizon.mywallet.api.model.Asset apiModel);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "shortName", target = "shortName")
    @Mapping(source = "longName", target = "longName")
    @Mapping(source = "strategy", target = "strategy")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "properties", target = "properties")
    br.com.eventhorizon.mywallet.api.model.Asset toApiModel(Asset model);
}
