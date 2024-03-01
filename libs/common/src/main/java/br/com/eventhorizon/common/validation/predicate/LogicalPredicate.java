package br.com.eventhorizon.common.validation.predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;
import java.util.function.Predicate;

import static br.com.eventhorizon.common.validation.predicate.ObjectPredicate.nullValue;
import static java.util.function.Predicate.not;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogicalPredicate {

    static <T> Predicate<T> is(final Predicate<T> predicate) {
        return predicate.and(is -> true);
    }

    public static Predicate<Boolean> isFalse() {
        return Predicate.<Boolean>not(nullValue()).and(not(isFalse -> isFalse));
    }

    public static <T> Predicate<T> isFalse(final Function<T, Boolean> function) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(function)))
                .and(not(function::apply));
    }

    public static Predicate<Boolean> isTrue() {
        return Predicate.<Boolean>not(nullValue()).and(is(isTrue -> isTrue));
    }

    public static <T> Predicate<T> isTrue(final Function<T, Boolean> function) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(function)))
                .and(function::apply);
    }
}
