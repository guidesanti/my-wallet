package br.com.eventhorizon.mywallet.ms.assets.infrastructure.http.controller.delegate;

import br.com.eventhorizon.mywallet.ms.assets.api.http.AssetTypesApiDelegate;
import br.com.eventhorizon.mywallet.ms.assets.api.http.model.*;
import br.com.eventhorizon.mywallet.ms.assets.infrastructure.http.model.mapper.AssetTypeDTOMapper;
import br.com.eventhorizon.mywallet.ms.assets.domain.services.AssetTypesService;
import br.com.eventhorizon.saga.SagaIdempotenceId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetTypesApiDelegateImpl implements AssetTypesApiDelegate {

    private final AssetTypesService assetTypesService;

    private final AssetTypeDTOMapper assetTypeDTOMapper = AssetTypeDTOMapper.INSTANCE;

    @Override
    public ResponseEntity<GetAllAssetTypes200Response> createAssetType(String xIdempotenceId,
                                                                       String xTraceId,
                                                                       CreateAssetTypeDTO createAssetTypeDTO) {
        var creatingAssetType = assetTypeDTOMapper.toBusiness(createAssetTypeDTO);
        var createdAssetType = assetTypesService.createAssetType(SagaIdempotenceId.of(xIdempotenceId), xTraceId, creatingAssetType);
        var createdAssetTypeDTO = assetTypeDTOMapper.toDTO(createdAssetType);
        log.info("Asset type created: {}", createAssetTypeDTO);
        var response = new GetAllAssetTypes200Response(ResponseStatus.SUCCESS).addDataItem(createdAssetTypeDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AssetTypesApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<GetAllAssetTypes200Response> getAllAssetTypes(String xTraceId) {
        return AssetTypesApiDelegate.super.getAllAssetTypes(xTraceId);
    }

    @Override
    public ResponseEntity<GetAllAssetTypes200Response> getOneAssetType(String xTraceId, String assetTypeId) {
        return AssetTypesApiDelegate.super.getOneAssetType(xTraceId, assetTypeId);
    }

    @Override
    public ResponseEntity<GetAllAssetTypes200Response> updateAssetType(String assetTypeId, String xIdempotenceId, String xTraceId, UpdateAssetTypeDTO updateAssetTypeDTO) {
        return AssetTypesApiDelegate.super.updateAssetType(assetTypeId, xIdempotenceId, xTraceId, updateAssetTypeDTO);
    }
}
