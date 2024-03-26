package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.processor.SubscriberPolledMessageBatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class KafkaSubscriberPolledMessageBatch<T> implements SubscriberPolledMessageBatch<T> {

    @Getter
    @ToString.Include
    private final TopicPartition topicPartition;

    private final LinkedList<KafkaSubscriberMessage<T>> list;

    @Getter
    @ToString.Include
    private final KafkaSubscriberPolledMessageBatchStatus status = new KafkaSubscriberPolledMessageBatchStatus();

    @Override
    public Optional<SubscriberMessage<T>> next() {
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.removeFirst());
    }

    @Override
    public List<SubscriberMessage<T>> next(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be greater than or equal to 0");
        }
        var subList = new ArrayList<SubscriberMessage<T>>();
        while (count > 0 && !list.isEmpty()) {
            subList.add(list.removeFirst());
            count--;
        }
        return subList;
    }
}
