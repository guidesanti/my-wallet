package br.com.eventhorizon.common.validation;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
public class ValidationError {

    String field;

    String code;

    String message;

    Object attemptedValue;
}
