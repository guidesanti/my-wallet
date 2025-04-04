package br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.http.controller;

import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.Producer2ApiDelegate;
import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.model.*;
import br.com.eventhorizon.mywallet.ms.kafka.producer.application.usecase.ProduceMessages2;
import br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.http.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer2ApiDelegateImpl implements Producer2ApiDelegate {

    private final DtoMapper DTO_MAPPER = DtoMapper.INSTANCE;

    private final ProduceMessages2 produceMessages;

    @Override
    public ResponseEntity<ProduceMessages200Response> produceMessagesByTime(String xIdempotenceId, String xTraceId, ProducerRequest2DTO producerRequest2DTO) {
        log.info("Producer params: {}", producerRequest2DTO);
        produceMessages.call(DTO_MAPPER.mapToInput(producerRequest2DTO));
        return ResponseEntity.ok(new ProduceMessages200Response(ResponseStatus.SUCCESS).data(new ProducerResponseDTO()));
    }
}
