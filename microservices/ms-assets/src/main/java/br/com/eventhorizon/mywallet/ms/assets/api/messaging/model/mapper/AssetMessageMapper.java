package br.com.eventhorizon.mywallet.ms.assets.api.messaging.model.mapper;

import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;
import br.com.eventhorizon.mywallet.ms.assets.proto.AssetProto;

public final class AssetMessageMapper {

    public static Asset toBusinessModel(AssetProto.AssetMessage assetMessage) {
        return Asset.builder()
                .id(assetMessage.getId())
                .shortName(assetMessage.getShortName())
                .longName(assetMessage.getLongName())
                .strategy(assetMessage.getStrategy())
                .type(assetMessage.getType())
                .properties(assetMessage.getPropertiesMap())
                .build();
    }

    public static AssetProto.AssetMessage toMessageModel(Asset model) {
        return AssetProto.AssetMessage.newBuilder()
                .setId(model.getId())
                .setShortName(model.getShortName())
                .setLongName(model.getLongName())
                .setStrategy(model.getStrategy())
                .setType(model.getType())
                .putAllProperties(model.getProperties())
                .build();
    }
}
