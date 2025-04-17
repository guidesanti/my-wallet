package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

@Slf4j
public class KafkaConsumerRebalanceListener implements ConsumerRebalanceListener {

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        log.warn("onPartitionsAssigned: {}", partitions);
    }

    @SneakyThrows
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        log.warn("onPartitionsRevoked: {}", partitions);
    }

    @SneakyThrows
    @Override
    public void onPartitionsLost(Collection<TopicPartition> partitions) {
        log.warn("onPartitionsLost: {}", partitions);
    }
}
