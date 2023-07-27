package br.com.eventhorizon.mywallet.repository;

import br.com.eventhorizon.mywallet.model.Asset;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends BaseRepository<Asset, String> {
}
