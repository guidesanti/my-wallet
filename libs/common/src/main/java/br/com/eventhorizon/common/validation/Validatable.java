package br.com.eventhorizon.common.validation;

public interface Validatable<T> {

    Validator<T> validator();
}
