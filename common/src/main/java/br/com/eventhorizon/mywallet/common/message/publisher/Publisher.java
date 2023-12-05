package br.com.eventhorizon.mywallet.common.message.publisher;

import br.com.eventhorizon.mywallet.common.message.Message;
import br.com.eventhorizon.mywallet.common.message.impl.DefaultMessage;

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
