package br.com.eventhorizon.mywallet.ms.assets.api.http.controller.delegate;

import br.com.eventhorizon.mywallet.common.common.DefaultErrors;
import br.com.eventhorizon.mywallet.common.exception.ClientErrorException;
import br.com.eventhorizon.mywallet.common.saga.InvalidSagaIdempotenceIdException;
import br.com.eventhorizon.mywallet.common.saga.SagaIdempotenceId;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.AssetDTO;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.DeleteAsset200Response;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.GetAllAssets200Response;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.ResponseStatus;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.mapper.AssetDTOMapper;
import br.com.eventhorizon.mywallet.ms.assets.business.service.AssetsService;
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
    public ResponseEntity<GetAllAssets200Response> createAsset(String traceId, String idempotenceId, AssetDTO asset) {
        log.info("Create asset request: {}", asset);
        validateCreateAssetRequest(traceId, idempotenceId, asset);
        var createdAsset = AssetDTOMapper.toApiModel(assetsService.createAsset(SagaIdempotenceId.of(idempotenceId),
                traceId, AssetDTOMapper.toBusinessModel(asset)));
        log.info("Created asset: {}", createdAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(createdAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void validateCreateAssetRequest(String traceId, String idempotenceId, AssetDTO asset) {
        try {
            SagaIdempotenceId.of(idempotenceId);
        } catch (InvalidSagaIdempotenceIdException ex) {
            throw new ClientErrorException(DefaultErrors.BAD_REQUEST.getCode(), "Invalid idempotence ID '" + idempotenceId + "'");
        }
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> updateAsset(String traceId,
                                                               String idempotenceId,
                                                               String assetId,
                                                               AssetDTO asset) {
        log.info("Update asset request: {}", asset);
        validateUpdateAssetRequest(traceId, idempotenceId, assetId, asset);
        var updatedAsset = AssetDTOMapper.toApiModel(assetsService.updateAsset(SagaIdempotenceId.of(idempotenceId),
                traceId, AssetDTOMapper.toBusinessModel(asset)));
        log.info("Updated asset: {}", updatedAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(updatedAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void validateUpdateAssetRequest(String traceId, String idempotenceId, String assetId, AssetDTO asset) {
        try {
            SagaIdempotenceId.of(idempotenceId);
        } catch (InvalidSagaIdempotenceIdException ex) {
            throw new ClientErrorException(DefaultErrors.BAD_REQUEST.getCode(), "Invalid idempotence ID '" + idempotenceId + "'");
        }
        if (asset.getId() == null) {
            asset.setId(assetId);
        }
        if (!asset.getId().equals(assetId)) {
            throw new ClientErrorException(DefaultErrors.BAD_REQUEST.getCode(), "Asset ID on path parameter '" + assetId +
                    "' does not match the asset ID '" + asset.getId() + "' on body");
        }
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
