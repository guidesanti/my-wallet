package br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.repository.impl;

import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.ms.assets.domain.entities.Asset;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.mapper.AssetDocumentMapper;
import br.com.eventhorizon.mywallet.ms.assets.application.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AssetRepositoryImpl implements AssetRepository {

    private final MongoDBAssetRepository assetDocumentRepository;

    @Autowired
    public AssetRepositoryImpl(MongoDBAssetRepository assetDocumentRepository) {
        this.assetDocumentRepository = assetDocumentRepository;
    }

    @Override
    public Asset create(Asset asset) throws DuplicateKeyException {
        try {
            return AssetDocumentMapper.toBusinessModel(assetDocumentRepository.create(AssetDocumentMapper.toPersistenceModel(asset)));
        } catch (org.springframework.dao.DuplicateKeyException ex) {
            throw new DuplicateKeyException("", ex);
        }
    }

    @Override
    public Asset update(Asset asset) {
        return AssetDocumentMapper.toBusinessModel(assetDocumentRepository.update(AssetDocumentMapper.toPersistenceModel(asset)));
    }

    @Override
    public void delete(String id) {
        assetDocumentRepository.deleteById(id);
    }

    @Override
    public List<Asset> findAll() {
        return assetDocumentRepository.findAll().stream()
                .map(AssetDocumentMapper::toBusinessModel)
                .toList();
    }

    @Override
    public Optional<Asset> findOne(String id) {
        return assetDocumentRepository.findById(id).map(AssetDocumentMapper::toBusinessModel);
    }
}
