package br.com.eventhorizon.mywallet.ms.assets.api.messaging.model.mapper;

import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;

public final class AssetMessageMapper {

    public static Asset toBusinessModel(AssetsProto.Asset asset) {
//        return Asset.builder()
//                .id(asset.getId())
//                .shortName(asset.getShortName())
//                .longName(asset.getLongName())
//                .strategy(asset.getStrategy())
//                .type(asset.getType())
//                .properties(asset.getPropertiesMap())
//                .build();
        return null;
    }

    public static AssetsProto.Asset toMessageModel(Asset model) {
//        return AssetsProto.Asset.newBuilder()
//                .setId(model.getId())
//                .setShortName(model.getShortName())
//                .setLongName(model.getLongName())
//                .setStrategy(model.getStrategy())
//                .setType(model.getType())
//                .putAllProperties(model.getProperties())
//                .build();
        return null;
    }
}
