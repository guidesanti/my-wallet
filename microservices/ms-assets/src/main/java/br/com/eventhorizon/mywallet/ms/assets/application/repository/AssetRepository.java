package br.com.eventhorizon.mywallet.ms.assets.application.repository;

import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.ms.assets.domain.entities.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetRepository {

    Asset create(Asset asset) throws DuplicateKeyException;

    Asset update(Asset asset);

    void delete(String id);

    List<Asset> findAll();

    Optional<Asset> findOne(String id);
}
