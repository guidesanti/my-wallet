package br.com.eventhorizon.common.validation.rule;

import java.util.function.Function;
import java.util.function.Predicate;


public class ValidationRuleImpl<T, P> implements ValidationRule<T, P> {

    private final Predicate<P> must;

    public ValidationRuleImpl(Predicate<P> must) {
        this.must = must;
    }

    @Override
    public void must(Predicate<P> predicate) {

    }

    @Override
    public void when(Predicate<P> predicate) {

    }

    @Override
    public void withFieldName(Function<?, String> function) {

    }

    @Override
    public void withMessage(Function<?, String> function) {

    }

    @Override
    public void withCode(Function<?, String> function) {

    }

    @Override
    public void withAttemptedValue(Function<?, Object> function) {

    }
}
