package br.com.eventhorizon.myinvestments.controller;

import br.com.eventhorizon.myinvestments.dto.AssetDTO;
import br.com.eventhorizon.myinvestments.dto.Response;
import br.com.eventhorizon.myinvestments.dto.ResponseStatus;
import br.com.eventhorizon.myinvestments.exception.ResourceNotFoundException;
import br.com.eventhorizon.myinvestments.service.AssetsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/assets")
@Slf4j
public class AssetsController {

  private final AssetsService assetsService;

  @Autowired
  public AssetsController(AssetsService assetsService) {
    this.assetsService = assetsService;
  }

  @PostMapping("")
  public ResponseEntity<Response<String>> createAsset(
      @Validated(AssetDTO.CreateValidation.class) @RequestBody() AssetDTO assetDTO
  ) {
    log.info("Create asset request: " + assetDTO);
    return new ResponseEntity<>(new Response<>(ResponseStatus.SUCCESS, assetsService.createAssetAsync(assetDTO), null), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<AssetDTO>> updateAsset(
          @PathVariable() String id,
          @RequestBody() AssetDTO assetDTO
  ) throws ResourceNotFoundException {
    assetDTO.setId(id);
    log.info("Update asset request: " + assetDTO);
    return new ResponseEntity<>(new Response<>(ResponseStatus.SUCCESS, assetsService.updateAsset(assetDTO), null), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<AssetDTO>> findAssetById(
          @PathVariable() String id
  ) throws ResourceNotFoundException {
    log.info("Find asset by id request: {}", id);
    return new ResponseEntity<>(new Response<>(ResponseStatus.SUCCESS, assetsService.findAssetById(id), null),
            HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<Response<List<AssetDTO>>> findAssets() {
    log.info("Find assets request");
    return new ResponseEntity<>(new Response<>(ResponseStatus.SUCCESS, assetsService.findAssets(), null),
            HttpStatus.OK);
  }
}
