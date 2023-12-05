package br.com.eventhorizon.mywallet.common.message;

public interface Message<T> {

    Headers headers();

    T content();
}
