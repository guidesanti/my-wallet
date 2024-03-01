package br.com.eventhorizon.common.validation;

import br.com.eventhorizon.common.validation.rule.ValidationRule;

public interface Validator<T> {

    ValidationResult validate(T target);

    <P> void rule(ValidationRule<T, P> validationRule);

    static <T1> Validator<T1> validator() {
        return new ValidatorImpl<>();
    }
}
