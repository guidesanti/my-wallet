package br.com.eventhorizon.common.building;

public interface Buildable<T> {

    Builder<T> builder();
}
