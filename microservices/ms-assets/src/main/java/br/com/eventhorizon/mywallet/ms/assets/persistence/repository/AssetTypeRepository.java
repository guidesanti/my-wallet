package br.com.eventhorizon.mywallet.ms.assets.persistence.repository;

import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.ms.assets.business.model.AssetType;

import java.util.List;
import java.util.Optional;

public interface AssetTypeRepository {

    AssetType create(AssetType assetType) throws DuplicateKeyException;

    AssetType update(AssetType assetType);

//    void delete(String id);

    List<AssetType> findAll();

    Optional<AssetType> findOne(String id);
}
