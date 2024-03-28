package br.com.eventhorizon.mywallet.ms.assets.domain.services;

import br.com.eventhorizon.common.exception.BusinessErrorException;
import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.ms.assets.domain.AssetsError;
import br.com.eventhorizon.mywallet.ms.assets.domain.entities.AssetType;
import br.com.eventhorizon.mywallet.ms.assets.application.repository.AssetTypeRepository;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.SagaResponse;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.publisher.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AssetTypesService {

    private final SagaTransactionExecutor sagaTransactionExecutor;

    private final SagaTransaction<AssetType, AssetType> createAssetTypeSagaTransaction;

    @Autowired
    public AssetTypesService(AssetTypeRepository assetTypeRepository,
                             SagaTransactionExecutor sagaTransactionExecutor,
                             SagaRepository sagaRepository,
                             SagaPublisher sagaPublisher) {
        this.sagaTransactionExecutor = sagaTransactionExecutor;
        this.createAssetTypeSagaTransaction = SagaTransaction.<AssetType, AssetType>builder()
                .id("create-asset-type-saga-transaction")
                .messagingProviderName("kafka")
                .source("")
                .sourceType(AssetType.class)
                .handler(new CreateAssetTypeRequestHandler(assetTypeRepository))
                .repository(sagaRepository)
                .publisher(sagaPublisher)
                .build();
    }

    public AssetType createAssetType(SagaIdempotenceId idempotenceId, String traceId, AssetType assetType) {
        SagaResponse<AssetType> sagaResponse = sagaTransactionExecutor.execute(
                createAssetTypeSagaTransaction,
                SagaMessage.<AssetType>builder()
                        .idempotenceId(idempotenceId)
                        .traceId(traceId)
                        .content(assetType)
                        .build());
        return sagaResponse.content();
    }

    public AssetType updateAssetType(SagaIdempotenceId idempotenceId, String traceId, AssetType assetType) {
        // TODO
        return null;
    }

    public void deleteAssetType(String assetTypeId) {
        // TODO
    }

    public List<AssetType> findAssetTypes() {
        // TODO
        return null;
    }

    public AssetType findAssetTypeById(String assetTypeId) {
        // TODO
        return null;
    }

    @RequiredArgsConstructor
    private static class CreateAssetTypeRequestHandler implements SagaSingleHandler<AssetType, AssetType> {

        private final AssetTypeRepository assetTypeRepository;

        @Override
        public SagaOutput<AssetType> handle(SagaMessage<AssetType> message) {
            var createAssetTypeRequest = message.content();
            try {
                var createdAsset = assetTypeRepository.create(createAssetTypeRequest);
                return SagaOutput.<AssetType>builder()
                        .response(SagaResponse.<AssetType>builder()
                                .idempotenceId(message.idempotenceId())
                                .content(createdAsset)
                                .build())
                        .build();
            } catch (DuplicateKeyException ex) {
                throw new BusinessErrorException(
                        AssetsError.ASSET_TYPE_ALREADY_EXIST.getCode(),
                        AssetsError.ASSET_TYPE_ALREADY_EXIST.getMessage(
                                createAssetTypeRequest.getName()),
                        ex);
            }
        }
    }
}
