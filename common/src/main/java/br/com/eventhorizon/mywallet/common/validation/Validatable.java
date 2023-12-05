package br.com.eventhorizon.mywallet.common.validation;

import java.util.List;

public interface Validatable {

    List<ValidationError> validate();
}
