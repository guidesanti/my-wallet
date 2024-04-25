package br.com.eventhorizon.messaging.provider.publisher;

import br.com.eventhorizon.common.messaging.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * The Publisher interface represents a publisher that can publish messages to a destination asynchronously.
 */
public interface Publisher {

    String UNSUPPORTED_TRANSACTIONS_ERROR_MESSAGE = "Transactions are not supported by this publisher";

    /**
     * Publishes a message asynchronously to a destination.
     *
     * @param <T> the type of the message content
     * @param destination the destination to publish the message to
     * @param content the content of the message
     * @return a Future containing the result of the publishing operation
     */
    default <T> Future<PublishingResult> publishAsync(String destination, T content) {
        return publishAsync(destination, Message.<T>builder()
                .content(content)
                .build());
    }

    /**
     * Publishes a message asynchronously to a destination.
     *
     * @param <T> the type of the message content
     * @param destination the destination to publish the message to
     * @param message the message to be published
     * @return a Future containing the result of the publishing operation
     */
    default <T> Future<PublishingResult> publishAsync(String destination, Message<T> message) {
        return publishAsync(PublishingRequest.<T>builder()
                .destination(destination)
                .message(message)
                .build());
    }

    /**
     * Publishes a message asynchronously to a destination.
     *
     * @param <T> the type of the message content
     * @param request the publishing request
     * @return a Future containing the result of the publishing operation
     */
    <T> Future<PublishingResult> publishAsync(PublishingRequest<T> request);

    /**
     * This method begins a transaction.
     */
    default void beginTransaction() {
        throw new UnsupportedOperationException(UNSUPPORTED_TRANSACTIONS_ERROR_MESSAGE);
    }

    /**
     * This method commits a transaction.
     */
    default void commitTransaction() {
        throw new UnsupportedOperationException(UNSUPPORTED_TRANSACTIONS_ERROR_MESSAGE);
    }

    /**
     * Executes a task within a transaction.
     *
     * @param task the task to be executed
     * @param <T> the type of the task's result
     * @return the result of the task
     * @throws Exception if an error occurs while executing the task
     */
    default <T> T executeInTransaction(Callable<T> task) throws Exception {
        throw new UnsupportedOperationException(UNSUPPORTED_TRANSACTIONS_ERROR_MESSAGE);
    }
}
