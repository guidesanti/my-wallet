package br.com.eventhorizon.common.validation.predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

import static br.com.eventhorizon.common.validation.predicate.ObjectPredicate.nullValue;
import static java.util.function.Predicate.not;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComparablePredicate {

    private static final Integer ZERO = 0;

    public static <E, T extends Comparable<E>> Predicate<T> lessThan(final E max) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(fn -> max)))
                .and(lessThan -> lessThan.compareTo(max) < ZERO);
    }

    public static <E, T extends Comparable<E>> Predicate<T> greaterThan(final E min) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(fn -> min)))
                .and(obj -> obj.compareTo(min) > ZERO);
    }
}
