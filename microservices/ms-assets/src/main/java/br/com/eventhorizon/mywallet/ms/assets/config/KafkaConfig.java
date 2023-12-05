package br.com.eventhorizon.mywallet.ms.assets.config;

import br.com.eventhorizon.mywallet.common.message.publisher.impl.KafkaPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@Import({
        KafkaPublisher.class
})
public class KafkaConfig {
}
