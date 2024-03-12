package br.com.eventhorizon.messaging.provider.publisher;

import br.com.eventhorizon.common.messaging.Message;

import java.util.concurrent.Future;

public interface Publisher {

    default <T> Future<PublisherResult> publishAsync(String destination, T content) {
        return publishAsync(destination, Message.<T>builder()
                .content(content)
                .build());
    }

    default <T> Future<PublisherResult> publishAsync(String destination, Message<T> message) {
        return publishAsync(PublisherRequest.<T>builder()
                .destination(destination)
                .message(message)
                .build());
    }

    <T> Future<PublisherResult> publishAsync(PublisherRequest<T> request);
}
