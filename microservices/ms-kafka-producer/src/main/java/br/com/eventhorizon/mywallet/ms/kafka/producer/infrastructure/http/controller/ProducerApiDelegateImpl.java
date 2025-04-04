package br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.http.controller;

import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.ProducerApiDelegate;
import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.model.ProduceMessages200Response;
import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.model.ProducerRequestDTO;
import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.model.ProducerResponseDTO;
import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.model.ResponseStatus;
import br.com.eventhorizon.mywallet.ms.kafka.producer.application.usecase.ProduceMessages;
import br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.http.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerApiDelegateImpl implements ProducerApiDelegate {

    private final DtoMapper DTO_MAPPER = DtoMapper.INSTANCE;

    private final ProduceMessages produceMessages;

    @Override
    public ResponseEntity<ProduceMessages200Response> produceMessages(String xIdempotenceId, String xTraceId, ProducerRequestDTO producerRequestDTO) {
        log.info("Producer params: {}", producerRequestDTO);
        produceMessages.call(DTO_MAPPER.mapToInput(producerRequestDTO));
        return ResponseEntity.ok(new ProduceMessages200Response(ResponseStatus.SUCCESS).data(new ProducerResponseDTO()));
    }
}
