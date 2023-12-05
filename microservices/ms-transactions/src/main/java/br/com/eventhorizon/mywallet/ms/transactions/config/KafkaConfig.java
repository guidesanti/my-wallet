package br.com.eventhorizon.mywallet.ms.transactions.config;

import br.com.eventhorizon.mywallet.common.message.publisher.impl.KafkaPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Map;

@Configuration
@EnableKafka
@Import({
        KafkaPublisher.class
})
public class KafkaConfig {
}
