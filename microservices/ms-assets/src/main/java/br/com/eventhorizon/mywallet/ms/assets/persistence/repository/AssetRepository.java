package br.com.eventhorizon.mywallet.ms.assets.persistence.repository;

import br.com.eventhorizon.mywallet.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetRepository {

    Asset create(Asset asset) throws DuplicateKeyException;

    Asset update(Asset asset);

    void delete(String id);

    List<Asset> findAll();

    Optional<Asset> findOne(String id);
}
