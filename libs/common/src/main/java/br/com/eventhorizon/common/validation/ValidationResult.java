package br.com.eventhorizon.common.validation;

import br.com.eventhorizon.common.validation.exception.ValidationException;
import lombok.*;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationResult {

    @Singular
    private final Collection<ValidationError> errors;

    public static ValidationResult success() {
        return new ValidationResult(Collections.emptyList());
    }

    public static ValidationResult fail(Collection<ValidationError> errors) {
        return new ValidationResult(Collections.unmodifiableCollection(errors));
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public void throwIfInvalid() {
        if (!isValid()) {
            throw ValidationException.of(this);
        }
    }
}
