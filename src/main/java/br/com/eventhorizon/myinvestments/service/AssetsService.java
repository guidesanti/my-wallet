package br.com.eventhorizon.myinvestments.service;

import br.com.eventhorizon.myinvestments.dto.AssetDTO;
import br.com.eventhorizon.myinvestments.exception.ResourceNotFoundException;
import br.com.eventhorizon.myinvestments.mapper.AssetMapper;
import br.com.eventhorizon.myinvestments.messaging.AssetManagementMessage;
import br.com.eventhorizon.myinvestments.model.Asset;
import br.com.eventhorizon.myinvestments.repository.AssetRepository;
import br.com.eventhorizon.myinvestments.repository.AssetTypesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class AssetsService {

    private final AssetRepository assetRepository;

    private final AssetMapper assetMapper;

    private final AssetTypesRepository assetTypesRepository;

    private final KafkaTemplate<String, AssetManagementMessage> kafkaTemplate;

    @Value("${br.com.eventhorizon.my-wallet.kafka.topic.asset-management}")
    private String assetManagementKafkaTopicName;

    @Autowired
    public AssetsService(AssetRepository assetRepository, AssetMapper assetMapper, AssetTypesRepository assetTypesRepository, KafkaTemplate<String, AssetManagementMessage> kafkaTemplate) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
        this.assetTypesRepository = assetTypesRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public AssetDTO createAsset(AssetDTO assetDTO) {
        var creatingAsset = assetMapper.toModel(assetDTO);
        log.info("Creating asset: {}", creatingAsset);
        var createdAsset = assetMapper.toDto(assetRepository.create(creatingAsset));
        log.info("Created asset: {}", createdAsset);
        return createdAsset;
    }

    public String createAssetAsync(AssetDTO assetDTO) {
        var asset = assetMapper.toModel(assetDTO);
        asset.setId(UUID.randomUUID().toString());

        List<Header> headers = List.of(new RecordHeader("traceId", "some-trace-id".getBytes()));
        var message = AssetManagementMessage.builder().operation(AssetManagementMessage.Operation.CREATE).asset(asset).build();
        var record = new ProducerRecord<>(assetManagementKafkaTopicName, null, asset.getId(), message, headers);
        var completableFuture = kafkaTemplate.send(record);
        AtomicBoolean ok = new AtomicBoolean(true);
        completableFuture.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Request to create asset '{}' failed while sending to Kafka", asset);
                ok.set(false);
            } else {
                log.info("Request to create asset '{}' sent to Kafka", asset);
            }
        });
        if (ok.get()) {
            return asset.getId();
        }
        throw new RuntimeException("Failed to request asset creation");
    }

    public AssetDTO updateAsset(AssetDTO assetDTO) throws ResourceNotFoundException {
        var updatingAsset = assetMapper.toModel(assetDTO);
        log.info("Updating asset: {}", updatingAsset);
        var updatedAsset = assetRepository.update(updatingAsset);
        if (updatedAsset == null) {
            throw new ResourceNotFoundException("Asset not found for ID " + assetDTO.getId());
        }
        log.info("Updated asset: {}", updatedAsset);
        return assetMapper.toDto(updatedAsset);
    }

    public AssetDTO findAssetById(String id) throws ResourceNotFoundException {
        var result = assetRepository.findById(id);
        if (result.isPresent()) {
            var foundAsset = result.get();
            log.info("Found asset: {}", foundAsset);
            return assetMapper.toDto(foundAsset);
        } else {
            throw new ResourceNotFoundException("Asset not found for ID " + id);
        }
    }

    public List<AssetDTO> findAssets() {
        var assets = assetRepository.findAll(PageRequest.of(0, 10)).stream().map(assetMapper::toDto).toList();
        log.info("Found assets: {}", assets.size());
        return assets;
    }
}
