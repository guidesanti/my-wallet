package br.com.eventhorizon.common.validation.rule;

import java.util.function.Function;
import java.util.function.Predicate;

public interface ValidationRule<T, P> extends Rule<P> {

    void must(Predicate<P> predicate);

    void when(Predicate<P> predicate);

    void withFieldName(Function<?, String> function);

    void withMessage(Function<?, String> function);

    void withCode(Function<?, String> function);

    void withAttemptedValue(Function<?, Object> function);
}
