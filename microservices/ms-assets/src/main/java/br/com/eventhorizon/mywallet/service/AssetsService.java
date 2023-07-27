package br.com.eventhorizon.mywallet.service;

import br.com.eventhorizon.mywallet.exception.ResourceNotFoundException;
import br.com.eventhorizon.mywallet.messaging.AssetManagementMessage;
import br.com.eventhorizon.mywallet.model.Asset;
import br.com.eventhorizon.mywallet.repository.AssetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AssetsService {

    private final AssetRepository assetRepository;

    private final KafkaTemplate<String, AssetManagementMessage> kafkaTemplate;

    @Value("${br.com.eventhorizon.my-wallet.kafka.topic.asset-management}")
    private String assetManagementKafkaTopicName;

    @Autowired
    public AssetsService(AssetRepository assetRepository, KafkaTemplate<String, AssetManagementMessage> kafkaTemplate) {
        this.assetRepository = assetRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

//    public String createAssetAsync(AssetDTO assetDTO) {
//        var asset = assetMapper.toModel(assetDTO);
//        asset.setId(UUID.randomUUID().toString());
//
//        List<Header> headers = List.of(new RecordHeader("traceId", "some-trace-id".getBytes()));
//        var message = AssetManagementMessage.builder().operation(AssetManagementMessage.Operation.CREATE).asset(asset).build();
//        var record = new ProducerRecord<>(assetManagementKafkaTopicName, null, asset.getId(), message, headers);
//        var completableFuture = kafkaTemplate.send(record);
//        AtomicBoolean ok = new AtomicBoolean(true);
//        completableFuture.whenComplete((result, throwable) -> {
//            if (throwable != null) {
//                log.error("Request to create asset '{}' failed while sending to Kafka", asset);
//                ok.set(false);
//            } else {
//                log.info("Request to create asset '{}' sent to Kafka", asset);
//            }
//        });
//        if (ok.get()) {
//            return asset.getId();
//        }
//        throw new RuntimeException("Failed to request asset creation");
//    }

    public Asset createAsset(Asset asset) {
        return assetRepository.create(asset);
    }

    public Asset updateAsset(Asset asset) {
        var results = assetRepository.update(asset);
        if (results == null) {
            throw new ResourceNotFoundException("Asset not found for ID " + asset.getId());
        }
        return results;
    }

    public void deleteAsset(String id) {
        assetRepository.deleteById(id);
    }

    public List<Asset> findAssets() {
        return assetRepository.findAll();
    }

    public Asset findAssetById(String id) throws ResourceNotFoundException {
        var result = assetRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Asset not found for ID " + id);
        }
    }
}
