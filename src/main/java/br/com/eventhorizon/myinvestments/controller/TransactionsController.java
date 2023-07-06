package br.com.eventhorizon.myinvestments.controller;

import br.com.eventhorizon.myinvestments.dto.AssetDTO;
import br.com.eventhorizon.myinvestments.dto.Response;
import br.com.eventhorizon.myinvestments.dto.ResponseStatus;
import br.com.eventhorizon.myinvestments.messaging.BuyAsset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/transactions")
@Slf4j
public class TransactionsController {

    private final KafkaTemplate<String, BuyAsset> kafkaTemplate;

    @Autowired
    public TransactionsController(KafkaTemplate<String, BuyAsset> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("")
    public ResponseEntity<Response<Void>> buyAsset(
            @RequestBody() AssetDTO assetDTO
    ) {
        kafkaTemplate.send("test", BuyAsset.builder().name("MTRE4").amount(10).price(5.56).build());
        log.info("Asset bought: " + assetDTO);
        return new ResponseEntity<>(new Response<>(ResponseStatus.SUCCESS, null, null), HttpStatus.OK);
    }
}
