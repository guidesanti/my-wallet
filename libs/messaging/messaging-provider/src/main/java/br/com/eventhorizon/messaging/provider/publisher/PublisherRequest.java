package br.com.eventhorizon.messaging.provider.publisher;

import br.com.eventhorizon.common.messaging.Message;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class PublisherRequest<T> {

    private final String destination;

    private final String orderingKey;

    private final Duration delay;

    private final Message<T> message;
}
