package br.com.eventhorizon.mywallet.ms.assets.infrastructure.http.controller.delegate;

import br.com.eventhorizon.mywallet.ms.assets.api.http.model.*;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.http.model.mapper.AssetDTOMapper;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.mywallet.ms.assets.domain.services.AssetsService;
import br.com.eventhorizon.mywallet.ms.assets.api.http.AssetsApiDelegate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AssetsApiDelegateImpl implements AssetsApiDelegate {

    private final AssetsService assetsService;

    @Override
    public ResponseEntity<GetAllAssets200Response> createAsset(String traceId,
                                                               String idempotenceId,
                                                               CreateAssetDTO createAssetDTO) {
        log.info("Create asset request: {}", createAssetDTO);
        var createdAsset = AssetDTOMapper.toApiModel(assetsService.createAsset(SagaIdempotenceId.of(idempotenceId),
                traceId, AssetDTOMapper.toCreateAssetCommandRequestMessageModel(createAssetDTO)));
        log.info("Created asset: {}", createdAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(createdAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> updateAsset(String traceId,
                                                               String idempotenceId,
                                                               String assetId,
                                                               UpdateAssetDTO updateAssetDTO) {
        log.info("Update asset request: {}", updateAssetDTO);
        var updatedAsset = AssetDTOMapper.toApiModel(assetsService.updateAsset(SagaIdempotenceId.of(idempotenceId),
                traceId, AssetDTOMapper.toUpdateAssetCommandRequestMessageModel(assetId, updateAssetDTO)));
        log.info("Updated asset: {}", updatedAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(updatedAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeleteAsset200Response> deleteAsset(String xRequestId, String assetId) {
        assetsService.deleteAsset(assetId);
        var response = new DeleteAsset200Response(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> getAllAssets(String xRequestId) {
        log.info("Get all assets request");
        var data = assetsService.findAssets().stream().map(AssetDTOMapper::toApiModel).toList();
        log.info("Found assets: {}", data);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> getOneAsset(String xRequestId, String assetId) {
        log.info("Get one asset request: {}", assetId);
        var foundAsset = AssetDTOMapper.toApiModel(assetsService.findAssetById(assetId));
        log.info("Found asset: {}", foundAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(foundAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
