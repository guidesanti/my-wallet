package br.com.eventhorizon.mywallet.common.message.publisher;

import br.com.eventhorizon.mywallet.common.message.Message;
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
