package br.com.eventhorizon.common.messaging;

public interface Message<T> {

    Headers headers();

    T content();
}
