package br.com.eventhorizon.mywallet.ms.assets.domain.exception;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;

public class AssetValidationException extends ValidationException {

    public AssetValidationException(ValidationResult validationResult) {
        super(validationResult);
    }
}
