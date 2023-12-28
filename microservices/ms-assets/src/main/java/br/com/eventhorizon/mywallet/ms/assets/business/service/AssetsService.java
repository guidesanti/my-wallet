package br.com.eventhorizon.mywallet.ms.assets.business.service;

import br.com.eventhorizon.common.exception.BusinessErrorException;
import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import br.com.eventhorizon.mywallet.common.proto.ResponseProto;
import br.com.eventhorizon.saga.*;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.publisher.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import br.com.eventhorizon.saga.transaction.DefaultSagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionExecutor;
import br.com.eventhorizon.mywallet.ms.assets.ApplicationProperties;
import br.com.eventhorizon.mywallet.ms.assets.api.messaging.model.mapper.AssetMessageMapper;
import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;
import br.com.eventhorizon.mywallet.ms.assets.persistence.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AssetsService {

    private final AssetRepository assetRepository;

    private final SagaTransactionExecutor sagaTransactionExecutor;

    private final SagaTransaction createAssetRequestSagaTransaction;

    private final SagaTransaction updateAssetRequestSagaTransaction;

    @Autowired
    public AssetsService(ApplicationProperties applicationProperties,
                         AssetRepository assetRepository,
                         SagaTransactionExecutor sagaTransactionExecutor,
                         SagaRepository sagaRepository,
                         SagaPublisher sagaPublisher) {
        this.assetRepository = assetRepository;
        this.sagaTransactionExecutor = sagaTransactionExecutor;
        this.createAssetRequestSagaTransaction = DefaultSagaTransaction.builder()
                .id("create-asset-saga-transaction")
                .handler(new CreateAssetRequestHandler(applicationProperties.getAssetCreatedKafkaTopicName(), assetRepository))
                .repository(sagaRepository)
                .publisher(sagaPublisher)
                .build();
        this.updateAssetRequestSagaTransaction = DefaultSagaTransaction.builder()
                .id("update-asset-saga-transaction")
                .handler(new UpdateAssetRequestHandler(applicationProperties.getAssetUpdatedKafkaTopicName(), assetRepository))
                .repository(sagaRepository)
                .publisher(sagaPublisher)
                .build();
    }

    public AssetsProto.CreateAssetCommandReply createAsset(SagaIdempotenceId idempotenceId, String traceId, AssetsProto.CreateAssetCommandRequest createAssetCommandRequest) {
        SagaResponse sagaResponse;
        sagaResponse = sagaTransactionExecutor.execute(
                createAssetRequestSagaTransaction,
                SagaMessage.builder()
                        .idempotenceId(idempotenceId)
                        .traceId(traceId)
                        .content(createAssetCommandRequest)
                        .build());
        return (AssetsProto.CreateAssetCommandReply) sagaResponse.content();
    }

    public AssetsProto.UpdateAssetCommandReply updateAsset(SagaIdempotenceId idempotenceId, String traceId, AssetsProto.UpdateAssetCommandRequest updateAssetCommandRequest) {
        SagaResponse sagaResponse;
        sagaResponse = sagaTransactionExecutor.execute(
                updateAssetRequestSagaTransaction,
                SagaMessage.builder()
                        .idempotenceId(idempotenceId)
                        .traceId(traceId)
                        .content(updateAssetCommandRequest)
                        .build());
        return (AssetsProto.UpdateAssetCommandReply) sagaResponse.content();
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

    public AssetsProto.GetAssetCommandReply findAssetById(SagaIdempotenceId idempotenceId, String traceId, AssetsProto.GetAssetCommandRequest getAssetCommandRequest) {
        // TODO
        return null;
    }

    @RequiredArgsConstructor
    private static class CreateAssetRequestHandler implements SagaSingleHandler {

        private final String assetCreatedKafkaTopicName;

        private final AssetRepository assetRepository;

        @Override
        public SagaOutput handle(SagaMessage message) {
            var createAssetCommandRequest = (AssetsProto.CreateAssetCommandRequest) message.content();
            try {
                var asset = Asset.builder()
                        .shortName(createAssetCommandRequest.getShortName())
                        .longName(createAssetCommandRequest.getLongName())
                        .strategy(createAssetCommandRequest.getStrategy())
                        .type(createAssetCommandRequest.getType())
                        .properties(createAssetCommandRequest.getPropertiesMap())
                        .build();
                var createdAsset = assetRepository.create(asset);
                var createdAssetMessage = AssetMessageMapper.toMessageModel(createdAsset);
                return SagaOutput.builder()
                        .response(SagaResponse.builder()
                                .idempotenceId(message.idempotenceId())
                                .content(AssetsProto.CreateAssetCommandReply.newBuilder()
                                        .setStatus(ResponseProto.Status.SUCCESS)
                                        .setData(createdAssetMessage)
                                        .build())
                                .build())
                        .event(SagaEvent.builder()
                                .originalIdempotenceId(message.idempotenceId())
                                .idempotenceId(message.idempotenceId().incrementIdempotenceStep(1))
                                .traceId(message.traceId())
                                .destination(assetCreatedKafkaTopicName)
                                .content(AssetsProto.AssetCreatedEvent.newBuilder()
                                        .setData(createdAssetMessage)
                                        .build())
                                .build())
                        .build();
            } catch (DuplicateKeyException ex) {
                throw new BusinessErrorException(
                        "ASSET_ALREADY_EXIST", "Asset already exist with shortName '"
                        + createAssetCommandRequest.getShortName() + "' or longName '"
                        + createAssetCommandRequest.getLongName() + "'",
                        ex);
            }
        }
    }

    @RequiredArgsConstructor
    private static class UpdateAssetRequestHandler implements SagaSingleHandler {

        private final String assetUpdatedKafkaTopicName;

        private final AssetRepository assetRepository;

        @Override
        public SagaOutput handle(SagaMessage message) throws Exception {
            var updateAssetCommandRequest = (AssetsProto.UpdateAssetCommandRequest) message.content();
            var asset = Asset.builder()
                    .id(updateAssetCommandRequest.getId())
                    .shortName(updateAssetCommandRequest.getShortName())
                    .longName(updateAssetCommandRequest.getLongName())
                    .strategy(updateAssetCommandRequest.getStrategy())
                    .type(updateAssetCommandRequest.getType())
                    .properties(updateAssetCommandRequest.getPropertiesMap())
                    .build();
            var updatedAsset = assetRepository.update(asset);
            if (updatedAsset == null) {
                throw new BusinessErrorException(
                        "ASSET_NOT_FOUND",
                        "Asset not found for ID " + updateAssetCommandRequest.getId());
            }
            var updatedAssetMessage = AssetMessageMapper.toMessageModel(updatedAsset);
            return SagaOutput.builder()
                    .response(SagaResponse.builder()
                            .idempotenceId(message.idempotenceId())
                            .content(AssetsProto.UpdateAssetCommandReply.newBuilder()
                                    .setStatus(ResponseProto.Status.SUCCESS)
                                    .setData(updatedAssetMessage)
                                    .build())
                            .build())
                    .event(SagaEvent.builder()
                            .originalIdempotenceId(message.idempotenceId())
                            .idempotenceId(message.idempotenceId().incrementIdempotenceStep(1))
                            .traceId(message.traceId())
                            .destination(assetUpdatedKafkaTopicName)
                            .content(AssetsProto.AssetUpdatedEvent.newBuilder()
                                    .setData(updatedAssetMessage)
                                    .build())
                            .build())
                    .build();
        }
    }
}
