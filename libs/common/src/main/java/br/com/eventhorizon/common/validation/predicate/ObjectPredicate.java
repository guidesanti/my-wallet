package br.com.eventhorizon.common.validation.predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectPredicate {

    public static <T> Predicate<T> objectEquals(final Object target) {
        return Predicate.<T>not(nullValue())
                .and(obj -> Objects.equals(obj, target));
    }

    public static <T> Predicate<T> objectEquals(final Function<T, Object> source, final Object target) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(source)))
                .and(not(nullValue(obj -> target)))
                .and(obj -> Objects.equals(source.apply(obj), target));
    }

    public static <T> Predicate<T> objectEquals(final Function<T, Object> source, final Function<T, Object> target) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(source)))
                .and(not(nullValue(target)))
                .and(obj -> Objects.equals(source.apply(obj), target.apply(obj)));
    }

    public static <T> Predicate<T> objectInstanceOf(final Class<?> clazz) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(fn -> clazz)))
                .and(clazz::isInstance);
    }

    public static <T> Predicate<T> objectInstanceOf(final Function<T, ?> source, final Class<?> clazz) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(source)))
                .and(obj -> objectInstanceOf(clazz).test(source.apply(obj)));
    }

    public static <T> Predicate<T> nullValue() {
        return Objects::isNull;
    }

    public static <T> Predicate<T> nullValue(final Function<T, ?> source) {
        return ObjectPredicate.<T>nullValue()
                .or(obj -> Objects.isNull(source))
                .or(obj -> Objects.isNull(source.apply(obj)));
    }
}
