package br.com.eventhorizon.common.validation.exception;

import br.com.eventhorizon.common.validation.ValidationResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class ValidationException extends RuntimeException {

    private final ValidationResult validationResult;
}
