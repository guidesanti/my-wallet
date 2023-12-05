package br.com.eventhorizon.mywallet.ms.assets.api.http.model.mapper;

import br.com.eventhorizon.mywallet.ms.assets.api.http.model.AssetDTO;
import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;

public final class AssetDTOMapper {

    public static Asset toBusinessModel(AssetDTO assetDTO) {
        return Asset.builder()
                .id(assetDTO.getId())
                .shortName(assetDTO.getShortName())
                .longName(assetDTO.getLongName())
                .strategy(assetDTO.getStrategy())
                .type(assetDTO.getType())
                .properties(assetDTO.getProperties())
                .build();
    }

    public static AssetDTO toApiModel(Asset asset) {
        var assetDTO = new AssetDTO(asset.getId(),
                asset.getShortName(),
                asset.getLongName(),
                asset.getStrategy(),
                asset.getType());
        assetDTO.setProperties(asset.getProperties());
        return assetDTO;
    }
}
