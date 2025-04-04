package br.com.eventhorizon.mywallet.ms.kafka.consumer.infrastructure.messaging.consumer;

import br.com.eventhorizon.messaging.provider.kafka.subscription.KafkaSingleSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.SubscriptionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TestSubscription {

    @Bean
    public Subscription<byte[]> testSubscription(TestHandler handler) {
        return KafkaSingleSubscription.<byte[]>builder()
                .id("test-kafka-subscription")
                .source("test")
                .handler(handler)
                .sourceType(byte[].class)
                .properties(subscriptionProperties())
                .configs(configs())
                .build();
    }

    private SubscriptionProperties subscriptionProperties() {
        return new SubscriptionProperties();
    }

    private Map<String, Object> configs() {
        var configs = new HashMap<String, Object>();
        return configs;
    }
}
