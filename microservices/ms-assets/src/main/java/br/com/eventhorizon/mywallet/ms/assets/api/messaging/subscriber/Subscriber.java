package br.com.eventhorizon.mywallet.ms.assets.api.messaging.subscriber;

import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Subscriber {

    @KafkaListener(topics = "${br.com.eventhorizon.mywallet.kafka.topics.assets-get-asset}")
    public void handle(ConsumerRecord<String, byte[]> consumerRecord) {
        log.info("Processing message start");
        try {
            var message = AssetsProto.GetAssetCommandRequest.parseFrom(consumerRecord.value());
            log.info("Headers: {}", consumerRecord.headers());
            log.info("Message: {}", message);
        } catch (Exception ex) {
            log.error("Failed to process message", ex);
        }
        log.info("Processing message end");
    }
}
