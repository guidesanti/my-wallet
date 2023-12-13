package br.com.eventhotizon.messaging.provider.publisher;

import br.com.eventhorizon.common.messaging.Message;
import br.com.eventhorizon.common.messaging.impl.DefaultMessage;

import java.util.concurrent.Future;

public interface Publisher {

    default <T> Future<PublisherResponse> publishAsync(String destination, T content) {
        return publishAsync(destination, DefaultMessage.<T>builder()
                .content(content)
                .build());
    }

    default <T> Future<PublisherResponse> publishAsync(String destination, Message<T> message) {
        return publishAsync(PublisherRequest.<T>builder()
                .destination(destination)
                .message(message)
                .build());
    }

    <T> Future<PublisherResponse> publishAsync(PublisherRequest<T> request);
}
