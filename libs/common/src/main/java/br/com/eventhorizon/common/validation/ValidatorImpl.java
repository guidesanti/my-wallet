package br.com.eventhorizon.common.validation;

import br.com.eventhorizon.common.validation.rule.ValidationRule;

public class ValidatorImpl<T> implements Validator<T> {

    @Override
    public ValidationResult validate(T target) {
        return null;
    }

    @Override
    public <P> void rule(ValidationRule<T, P> validationRule) {
        //TODO
    }
}
