package br.com.eventhorizon.messaging.provider.publisher;

public class PublisherException extends RuntimeException {

    public PublisherException(String message) {
        super(message);
    }

    public PublisherException(String message, Throwable cause) {
        super(message, cause);
    }
}
