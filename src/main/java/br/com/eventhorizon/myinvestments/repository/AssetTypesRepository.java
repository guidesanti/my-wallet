package br.com.eventhorizon.myinvestments.repository;

import br.com.eventhorizon.myinvestments.model.AssetType;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypesRepository extends BaseRepository<AssetType, String> {
}
