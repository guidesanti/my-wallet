package br.com.eventhorizon.mywallet.ms.assets.persistence.repository.impl;

import br.com.eventhorizon.common.repository.BaseRepository;
import br.com.eventhorizon.mywallet.ms.assets.persistence.model.AssetDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBAssetRepository extends BaseRepository<AssetDocument, String> {
}
