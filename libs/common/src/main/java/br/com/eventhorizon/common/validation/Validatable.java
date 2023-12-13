package br.com.eventhorizon.common.validation;

import java.util.List;

public interface Validatable {

    List<ValidationError> validate();
}
