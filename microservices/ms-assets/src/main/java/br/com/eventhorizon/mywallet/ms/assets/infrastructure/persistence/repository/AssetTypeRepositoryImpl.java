package br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.repository.impl;

import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.ms.assets.domain.entities.AssetType;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.mapper.AssetTypeDocumentMapper;
import br.com.eventhorizon.mywallet.ms.assets.application.repository.AssetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AssetTypeRepositoryImpl implements AssetTypeRepository {

    private final AssetTypeDocumentMapper assetTypeDocumentMapper = AssetTypeDocumentMapper.INSTANCE;

//    private final MongoDBAssetTypeRepository assetTypeDocumentRepository;

    @Override
    public AssetType create(AssetType assetType) throws DuplicateKeyException {
        try {
//            return assetTypeDocumentMapper.toBusiness(assetTypeDocumentRepository.create(assetTypeDocumentMapper.toPersistence(assetType)));
            return null;
        } catch (org.springframework.dao.DuplicateKeyException ex) {
            throw new DuplicateKeyException("Asset type already exists", ex);
        }
    }

    @Override
    public AssetType update(AssetType assetType) {
        // TODO
        return null;
    }

    @Override
    public List<AssetType> findAll() {
        // TODO
        return null;
    }

    @Override
    public Optional<AssetType> findOne(String id) {
        // TODO
        return Optional.empty();
    }
}
