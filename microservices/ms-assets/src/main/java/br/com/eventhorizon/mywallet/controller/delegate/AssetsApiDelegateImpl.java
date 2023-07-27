package br.com.eventhorizon.mywallet.controller.delegate;

import br.com.eventhorizon.mywallet.api.AssetsApiDelegate;
import br.com.eventhorizon.mywallet.api.model.Asset;
import br.com.eventhorizon.mywallet.api.model.DeleteAsset200Response;
import br.com.eventhorizon.mywallet.api.model.GetAllAssets200Response;
import br.com.eventhorizon.mywallet.api.model.ResponseStatus;
import br.com.eventhorizon.mywallet.mapper.AssetMapper;
import br.com.eventhorizon.mywallet.service.AssetsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AssetsApiDelegateImpl implements AssetsApiDelegate {

    private final AssetsService assetsService;

    private final AssetMapper assetMapper;

    @Autowired
    public AssetsApiDelegateImpl(AssetsService assetsService, AssetMapper assetMapper) {
        this.assetsService = assetsService;
        this.assetMapper = assetMapper;
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> createAsset(String xTraceId, Asset asset) {
        log.info("Create asset request: {}", asset);
        var createdAsset = assetMapper.toApiModel(assetsService.createAsset(assetMapper.toModel(asset)));
        log.info("Created asset: {}", createdAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(createdAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> updateAsset(String assetId, String xTraceId, Asset asset) {
        log.info("Update asset request: {}", asset);

        if (asset.getId() != null && !asset.getId().toString().equals(assetId)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        var updatedAsset = assetMapper.toApiModel(assetsService.updateAsset(assetMapper.toModel(asset)));
        log.info("Updated asset: {}", updatedAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(updatedAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeleteAsset200Response> deleteAsset(String assetId, String xTraceId) {
        assetsService.deleteAsset(assetId);
        var response = new DeleteAsset200Response(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> getAllAssets(String xTraceId) {
        log.info("Get all assets request");
        var data = assetsService.findAssets().stream().map(assetMapper::toApiModel).toList();
        log.info("Found assets: {}", data);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllAssets200Response> getOneAsset(String assetId, String xTraceId) {
        log.info("Get one asset request: {}", assetId);
        var foundAsset = assetMapper.toApiModel(assetsService.findAssetById(assetId));
        log.info("Found asset: {}", foundAsset);
        var response = new GetAllAssets200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(foundAsset));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
