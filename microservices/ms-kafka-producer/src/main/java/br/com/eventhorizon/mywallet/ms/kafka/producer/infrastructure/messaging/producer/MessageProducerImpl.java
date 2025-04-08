package br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.messaging.producer;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.messaging.provider.Header;
import br.com.eventhorizon.messaging.provider.kafka.publisher.KafkaPublisher;
import br.com.eventhorizon.messaging.provider.utils.HeaderUtils;
import br.com.eventhorizon.mywallet.ms.kafka.producer.ApplicationProperties;
import br.com.eventhorizon.mywallet.ms.kafka.producer.application.messaging.MessageProducer;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Message;
import br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.messaging.mapper.ProtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerImpl implements MessageProducer {

    private final ApplicationProperties applicationProperties;

    private final KafkaPublisher kafkaPublisher;

    @Override
    public void produceBatchMarkerMessage(String destination, Message message, BatchMarker batchMarker) {
        try {
            kafkaPublisher.publishAsync(destination, br.com.eventhorizon.common.messaging.Message.builder()
                    .headers(HeaderUtils.buildBasePlatformHeaders(applicationProperties.getConfig(),
                            Headers.builder()
                                    .header(Header.CREATED_AT.getName(), DateTimeFormatter.ISO_INSTANT.format(message.createdAt()))
                                    .build()))
                    .header("batch-id", message.batchId().toString())
                    .header("batch-marker", batchMarker.name())
                    .header("sequence", String.valueOf(message.sequence()))
                    .content(ProtoMapper.INSTANCE.mapToProto(message).toByteArray())
                    .build()).get();
        } catch (Exception ex) {
            log.error("Error while publishing message {}", message, ex);
        }
    }

    @Override
    public void produce(String destination, Message message) {
        try {
            kafkaPublisher.publishAsync(destination, br.com.eventhorizon.common.messaging.Message.builder()
                    .headers(HeaderUtils.buildBasePlatformHeaders(applicationProperties.getConfig(),
                            Headers.builder()
                                    .header(Header.CREATED_AT.getName(), DateTimeFormatter.ISO_INSTANT.format(message.createdAt()))
                                    .build()))
                    .header("batch-id", message.batchId().toString())
                    .header("sequence", String.valueOf(message.sequence()))
                    .content(ProtoMapper.INSTANCE.mapToProto(message).toByteArray())
                    .build()).get();
        } catch (Exception ex) {
            log.error("Error while publishing message {}", message, ex);
        }
    }
}
