package br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.mapper;

import br.com.eventhorizon.mywallet.ms.assets.domain.entities.Asset;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.AssetDocument;

public final class AssetDocumentMapper {

    public static AssetDocument toPersistenceModel(Asset asset) {
//        return AssetDocument.builder()
//                .id(asset.getId())
//                .shortName(asset.getShortName())
//                .longName(asset.getLongName())
//                .strategy(asset.getStrategy())
//                .type(asset.getType())
//                .properties(asset.getProperties())
//                .build();
        return null;
    }

    public static Asset toBusinessModel(AssetDocument assetDocument) {
//        return Asset.builder()
//                .id(assetDocument.getId())
//                .shortName(assetDocument.getShortName())
//                .longName(assetDocument.getLongName())
//                .strategy(assetDocument.getStrategy())
//                .type(assetDocument.getType())
//                .properties(assetDocument.getProperties())
//                .build();
        return null;
    }
}
