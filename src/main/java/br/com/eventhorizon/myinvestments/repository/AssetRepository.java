package br.com.eventhorizon.myinvestments.repository;

import br.com.eventhorizon.myinvestments.model.Asset;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends BaseRepository<Asset, String> {
}
