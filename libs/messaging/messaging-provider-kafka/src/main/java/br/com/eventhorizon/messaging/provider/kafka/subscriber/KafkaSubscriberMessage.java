package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class KafkaSubscriberMessage<T> extends SubscriberMessage<T> {

    private final long offset;

    private KafkaSubscriberMessage(Builder<?, T> builder) {
        super(builder);
        this.offset = builder.offset;
    }

    public long offset() {
        return offset;
    }

    public static <T> Builder<?, T> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, T>, T> extends SubscriberMessage.Builder<B, T> {

        private long offset;

        public B offset(long offset) {
            this.offset = offset;
            return self();
        }

        @Override
        public KafkaSubscriberMessage<T> build() {
            return new KafkaSubscriberMessage<>(this);
        }
    }
}
