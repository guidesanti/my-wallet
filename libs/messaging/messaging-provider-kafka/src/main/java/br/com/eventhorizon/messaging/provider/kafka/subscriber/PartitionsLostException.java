package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import lombok.Getter;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

@Getter
public class PartitionsLostException extends RuntimeException {

    private final Collection<TopicPartition> partitions;

    public PartitionsLostException(String message, Collection<TopicPartition> partitions) {
        super(message);
        this.partitions = partitions;
    }
}
