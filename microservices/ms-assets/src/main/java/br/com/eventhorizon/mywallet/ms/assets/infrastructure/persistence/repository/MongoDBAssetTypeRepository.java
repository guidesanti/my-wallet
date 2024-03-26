package br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.repository;

import br.com.eventhorizon.common.repository.BaseRepository;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.AssetTypeDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBAssetTypeRepository extends BaseRepository<AssetTypeDocument, String> {
}
