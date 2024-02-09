package br.com.eventhorizon.mywallet.ms.assets.api.http.model.mapper;

import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.AssetDTO;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.CreateAssetDTO;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.UpdateAssetDTO;
import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

//@Mapper(
//        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
//)
public final class AssetDTOMapper {

//    AssetDTOMapper INSTANCE = Mappers.getMapper(AssetDTOMapper.class);
//
//    @Mapping(target = "id", source = "asset.id")
//    @Mapping(target = "shortName", source = "asset.shortName")
//    @Mapping(target = "longName", source = "asset.longName")
//    @Mapping(target = "type", source = "asset.type")
//    @Mapping(target = "description", source = "asset.description")
//    @Mapping(target = "properties", source = "asset.properties")
//    Asset toDomainModel(AssetDTO asset);

    public static AssetDTO toApiModel(Asset asset) {
//        var assetDTO = new AssetDTO(asset.getId(),
//                asset.getShortName(),
//                asset.getLongName(),
//                asset.getStrategy(),
//                asset.getType());
//        assetDTO.setProperties(asset.getProperties());
//        return assetDTO;
        return null;
    }

    public static AssetDTO toApiModel(AssetsProto.CreateAssetCommandReply createAssetCommandReply) {
        var assetMessage = createAssetCommandReply.getData();
        var assetDTO = new AssetDTO(assetMessage.getId(),
                assetMessage.getShortName(),
                assetMessage.getLongName(),
                assetMessage.getStrategy(),
                assetMessage.getType());
        assetDTO.setProperties(assetMessage.getPropertiesMap());
        return assetDTO;
    }

    public static AssetDTO toApiModel(AssetsProto.UpdateAssetCommandReply updateAssetCommandReply) {
        var assetMessage = updateAssetCommandReply.getData();
        var assetDTO = new AssetDTO(assetMessage.getId(),
                assetMessage.getShortName(),
                assetMessage.getLongName(),
                assetMessage.getStrategy(),
                assetMessage.getType());
        assetDTO.setProperties(assetMessage.getPropertiesMap());
        return assetDTO;
    }

    public static AssetsProto.CreateAssetCommandRequest toCreateAssetCommandRequestMessageModel(CreateAssetDTO createAssetDTO) {
        return AssetsProto.CreateAssetCommandRequest.newBuilder()
                .setShortName(createAssetDTO.getShortName())
                .setLongName(createAssetDTO.getLongName())
                .setStrategy(createAssetDTO.getStrategy())
                .setType(createAssetDTO.getType())
                .putAllProperties(createAssetDTO.getProperties())
                .build();
    }

    public static AssetsProto.UpdateAssetCommandRequest toUpdateAssetCommandRequestMessageModel(String assetId, UpdateAssetDTO updateAssetDTO) {
        return AssetsProto.UpdateAssetCommandRequest.newBuilder()
                .setId(assetId)
                .setShortName(updateAssetDTO.getShortName())
                .setLongName(updateAssetDTO.getLongName())
                .setStrategy(updateAssetDTO.getStrategy())
                .setType(updateAssetDTO.getType())
                .putAllProperties(updateAssetDTO.getProperties())
                .build();
    }
}
