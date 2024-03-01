package br.com.eventhorizon.common.validation.rule;

public interface Rule<P> {

    default boolean apply(P value) {
        return true;
    }

    default boolean apply(Object obj, P value) {
        return apply(value);
    }

    default boolean support(P value) {
        return true;
    }
}
