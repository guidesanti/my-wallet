package br.com.eventhorizon.mywallet.repository;

import br.com.eventhorizon.mywallet.model.AssetType;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypesRepository extends BaseRepository<AssetType, String> {
}
