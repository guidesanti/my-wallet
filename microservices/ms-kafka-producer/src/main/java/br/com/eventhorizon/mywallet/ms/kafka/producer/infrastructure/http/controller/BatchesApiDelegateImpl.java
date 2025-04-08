package br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.http.controller;

import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.BatchesApiDelegate;
import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.model.*;
import br.com.eventhorizon.mywallet.ms.kafka.producer.application.usecase.ProduceMessages;
import br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.http.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchesApiDelegateImpl implements BatchesApiDelegate {

    private final DtoMapper DTO_MAPPER = DtoMapper.INSTANCE;

    private final ProduceMessages produceMessages;

    @Override
    public ResponseEntity<ProduceMessagesByTime200Response> produceMessagesByTime(String xIdempotenceId, String xTraceId, ProducerRequestDTO producerRequestDTO) {
        log.info("Producer params: {}", producerRequestDTO);
        produceMessages.call(DTO_MAPPER.mapToInput(producerRequestDTO));
        return ResponseEntity.ok(new ProduceMessagesByTime200Response(ResponseStatus.SUCCESS).data(new ProducerResponseDTO()));
    }
}
