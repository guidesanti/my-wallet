package br.com.eventhorizon.mywallet.ms.assets.business.service;

import br.com.eventhorizon.common.exception.BaseException;
import br.com.eventhorizon.common.exception.BusinessErrorException;
import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.saga.*;
import br.com.eventhorizon.saga.content.SagaContent;
import br.com.eventhorizon.saga.content.serializer.impl.DefaultSagaContentSerializer;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionManager;
import br.com.eventhorizon.mywallet.ms.assets.ApplicationProperties;
import br.com.eventhorizon.mywallet.ms.assets.api.messaging.model.mapper.AssetMessageMapper;
import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;
import br.com.eventhorizon.mywallet.ms.assets.persistence.repository.AssetRepository;
import br.com.eventhorizon.mywallet.ms.assets.proto.AssetProto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AssetsService {

    private final AssetRepository assetRepository;

    private final SagaTransactionManager sagaTransactionManager;

    private final SagaTransaction createAssetRequestSagaTransaction;

    private final SagaTransaction updateAssetRequestSagaTransaction;

    @Autowired
    public AssetsService(ApplicationProperties applicationProperties,
                         AssetRepository assetRepository,
                         SagaRepository sagaRepository,
                         SagaPublisher sagaPublisher) {
        this.assetRepository = assetRepository;
        this.sagaTransactionManager = new SagaTransactionManager();
        this.createAssetRequestSagaTransaction = SagaTransaction.builder()
                .handler(new CreateAssetRequestHandler(applicationProperties.getService().getName(), assetRepository))
                .repository(sagaRepository)
                .publisher(sagaPublisher)
                .serializer(new DefaultSagaContentSerializer(Asset.class))
                .serializer(new DefaultSagaContentSerializer(AssetProto.AssetMessage.class))
                .build();
        this.updateAssetRequestSagaTransaction = SagaTransaction.builder()
                .handler(new UpdateAssetRequestHandler(applicationProperties.getService().getName(), assetRepository))
                .repository(sagaRepository)
                .publisher(sagaPublisher)
                .serializer(new DefaultSagaContentSerializer(Asset.class))
                .serializer(new DefaultSagaContentSerializer(AssetProto.AssetMessage.class))
                .build();
    }

    public Asset createAsset(SagaIdempotenceId idempotenceId, String traceId, Asset asset) {
        SagaResponse sagaResponse;
        try {
            sagaResponse = sagaTransactionManager.execute(
                    createAssetRequestSagaTransaction,
                    SagaMessage.builder()
                            .idempotenceId(idempotenceId)
                            .traceId(traceId)
                            .content(SagaContent.of(asset))
                            .build());
        } catch (BaseException ex) {
          throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return (Asset) sagaResponse.content().getContent();
    }

    public Asset updateAsset(SagaIdempotenceId idempotenceId, String traceId, Asset asset) {
        SagaResponse sagaResponse;
        try {
            sagaResponse = sagaTransactionManager.execute(
                    updateAssetRequestSagaTransaction,
                    SagaMessage.builder()
                            .idempotenceId(idempotenceId)
                            .traceId(traceId)
                            .content(SagaContent.of(asset))
                            .build());
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return (Asset) sagaResponse.content().getContent();
    }

    public void deleteAsset(String id) {
        assetRepository.delete(id);
    }

    public List<Asset> findAssets() {
        return assetRepository.findAll();
    }

    public Asset findAssetById(String id) {
        var result = assetRepository.findOne(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new BusinessErrorException("ASSET_NOT_FOUND", "Asset not found for ID " + id);
        }
    }

    @RequiredArgsConstructor
    private static class CreateAssetRequestHandler implements SagaSingleHandler {

        private final String source;

        private final AssetRepository assetRepository;

        @Override
        public SagaOutput handle(SagaMessage message) {
            var createAssetRequest = (Asset) message.content().getContent();
            try {
                var createdAsset = assetRepository.create(createAssetRequest);
                return SagaOutput.builder()
                        .response(SagaResponse.builder()
                                .idempotenceId(message.idempotenceId())
                                .content(SagaContent.of(createdAsset))
                                .build())
                        .event(SagaEvent.builder()
                                .idempotenceId(message.idempotenceId())
                                .traceId(message.traceId())
                                .destination("local-asset-management")
                                .headers(SagaHeaders.builder()
                                        .header("custom-header", "custom-header-value")
                                        .build())
                                .content(SagaContent.of(AssetMessageMapper.toMessageModel(createdAsset)))
                                .build())
                        .build();
            } catch (DuplicateKeyException ex) {
                throw new BusinessErrorException(
                        "ASSET_ALREADY_EXIST", "Asset already exist with shortName '" +
                        createAssetRequest.getShortName() + "' or longName '" + createAssetRequest.getLongName() + "'",
                        ex);
            }
        }
    }

    @RequiredArgsConstructor
    private static class UpdateAssetRequestHandler implements SagaSingleHandler {

        private final String source;

        private final AssetRepository assetRepository;

        @Override
        public SagaOutput handle(SagaMessage message) throws Exception {
            var updateAssetRequest = (Asset) message.content().getContent();
            var updatedAsset = assetRepository.update(updateAssetRequest);
            if (updatedAsset == null) {
                throw new BusinessErrorException(
                        "ASSET_NOT_FOUND",
                        "Asset not found for ID " + updateAssetRequest.getId());
            }
            return SagaOutput.builder()
                    .response(SagaResponse.builder()
                            .idempotenceId(message.idempotenceId())
                            .content(SagaContent.of(updatedAsset))
                            .build())
                    .event(SagaEvent.builder()
                            .idempotenceId(message.idempotenceId())
                            .traceId(message.traceId())
                            .destination("local-asset-management")
                            .headers(SagaHeaders.builder()
                                    .header("custom-header", "custom-header-value")
                                    .build())
                            .content(SagaContent.of(AssetMessageMapper.toMessageModel(updatedAsset)))
                            .build())
                    .build();
        }
    }
}
