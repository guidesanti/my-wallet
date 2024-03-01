package br.com.eventhorizon.common.validation.predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static br.com.eventhorizon.common.validation.predicate.ComparablePredicate.greaterThan;
import static br.com.eventhorizon.common.validation.predicate.ComparablePredicate.lessThan;
import static br.com.eventhorizon.common.validation.predicate.ObjectPredicate.objectEquals;
import static br.com.eventhorizon.common.validation.predicate.ObjectPredicate.nullValue;
import static java.util.function.Predicate.not;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringPredicate {

    public static Predicate<String> stringIsAlpha() {
        return not(stringEmptyOrNull())
                .and(s -> s.chars().allMatch(Character::isLetter));
    }

    public static <T> Predicate<T> stringIsAlpha(final Function<T, String> source) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringIsAlpha().test(source.apply(obj)));
    }

    public static Predicate<String> isAlphaNumeric() {
        return not(stringEmptyOrNull())
                .and(isNumeric -> isNumeric.chars().allMatch(Character::isLetterOrDigit));
    }

    public static <T> Predicate<T> isAlphaNumeric(final Function<T, String> source) {
        return Predicate.<T>not(nullValue())
                .and(obj -> isAlphaNumeric().test(source.apply(obj)));
    }

    public static Predicate<String> isDate(final String pattern) {
        return Predicate.<String>not(nullValue())
                .and(isDate -> not(stringEmptyOrNull()).test(pattern))
                .and(isDate -> {
                    try {
                        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                        return Objects.nonNull(LocalDate.parse(isDate, dateFormat));
                    } catch (final IllegalArgumentException | DateTimeParseException ex) {
                        return false;
                    }
                });
    }

    public static <T> Predicate<T> isDate(final Function<T, String> source, final String pattern) {
        return Predicate.<T>not(nullValue())
                .and(obj -> isDate(pattern).test(source.apply(obj)));
    }

    public static Predicate<String> isDateTime(final String pattern) {
        return Predicate.<String>not(nullValue())
                .and(isDateTime -> not(stringEmptyOrNull()).test(pattern))
                .and(isDateTime -> {
                    try {
                        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                        return Objects.nonNull(LocalDateTime.parse(isDateTime, dateFormat));
                    } catch (final IllegalArgumentException | DateTimeParseException ex) {
                        return false;
                    }
                });
    }

    public static <T> Predicate<T> isDateTime(final Function<T, String> source, final String pattern) {
        return Predicate.<T>not(nullValue())
                .and(obj -> isDateTime(pattern).test(source.apply(obj)));
    }

    public static Predicate<String> isNumber() {
        return not(stringEmptyOrNull()).and(isNumber -> {
            try {
                new BigDecimal(isNumber);
            } catch (final NumberFormatException e) {
                return false;
            }
            return true;
        });
    }

    public static <T> Predicate<T> isNumber(final Function<T, String> source) {
        return Predicate.<T>not(nullValue())
                .and(obj -> isNumber().test(source.apply(obj)));
    }

    public static Predicate<String> isNumeric() {
        return not(stringEmptyOrNull())
                .and(isNumeric -> isNumeric.chars().allMatch(Character::isDigit));
    }

    public static <T> Predicate<T> isNumeric(final Function<T, String> source) {
        return Predicate.<T>not(nullValue())
                .and(obj -> isNumeric().test(source.apply(obj)));
    }

    public static Predicate<String> isTime(final String pattern) {
        return Predicate.<String>not(nullValue())
                .and(isTime -> not(stringEmptyOrNull()).test(pattern))
                .and(isTime -> {
                    try {
                        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                        return Objects.nonNull(LocalTime.parse(isTime, dateFormat));
                    } catch (final IllegalArgumentException | DateTimeParseException ex) {
                        return false;
                    }
                });
    }

    public static <T> Predicate<T> isTime(final Function<T, String> source, final String pattern) {
        return Predicate.<T>not(nullValue())
                .and(obj -> isTime(pattern).test(source.apply(obj)));
    }

    public static Predicate<String> stringContains(final String str) {
        return Predicate.<String>not(nullValue())
                .and(stringContains -> not(nullValue()).test(str))
                .and(stringContains -> stringContains.contains(str));
    }

    public static <T> Predicate<T> stringContains(final Function<T, String> source, final String str) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringContains(str).test(source.apply(obj)));
    }

    public static Predicate<String> stringEmptyOrNull() {
        return LogicalPredicate.<String>is(nullValue())
                .or(String::isEmpty);
    }

    public static <T> Predicate<T> stringEmptyOrNull(final Function<T, String> source) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringEmptyOrNull().test(source.apply(obj)));
    }

    public static <T> Predicate<T> stringEquals(final String value) {
        return Predicate.<T>not(nullValue())
                .and(obj -> obj.equals(value));
    }

    public static <T> Predicate<T> stringEquals(final Function<T, String> source, final String value) {
        return Predicate.<T>not(nullValue())
                .and(obj -> not(nullValue()).test(source.apply(obj)))
                .and(obj -> stringEquals(value).test(source.apply(obj)));
    }

    public static <T> Predicate<T> stringEquals(final Function<T, String> source, final Function<T, String> target) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringEquals(source, target.apply(obj)).test(obj));
    }

    public static Predicate<String> stringEqualsIgnoreCase(final String value) {
        return Predicate.<String>not(nullValue())
                .and(obj -> not(nullValue()).test(value))
                .and(obj -> obj.equalsIgnoreCase(value));
    }

    public static <T> Predicate<T> stringEqualsIgnoreCase(final Function<T, String> source, final String value) {
        return Predicate.<T>not(nullValue())
                .and(obj -> not(nullValue()).test(source.apply(obj)))
                .and(obj -> stringEqualsIgnoreCase(value).test(source.apply(obj)));
    }

    public static <T> Predicate<T> stringEqualsIgnoreCase(final Function<T, String> source, final Function<T, String> target) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringEqualsIgnoreCase(source, target.apply(obj)).test(obj));
    }

    public static Predicate<String> stringMatches(final String regex) {
        return Predicate.<String>not(nullValue())
                .and(stringMatches -> not(nullValue()).test(regex))
                .and(stringMatches -> stringMatches.matches(regex));
    }

    public static <T> Predicate<T> stringMatches(final Function<T, String> source, final String regex) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringMatches(regex).test(source.apply(obj)));
    }

    public static Predicate<String> stringSize(final Integer size) {
        return Predicate.<String>not(nullValue())
                .and(stringSize -> not(nullValue()).test(size))
                .and(stringSize -> objectEquals(size).test(stringSize.length()));
    }

    public static <T> Predicate<T> stringSize(final Function<T, String> source, final Integer size) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringSize(size).test(source.apply(obj)));
    }

    public static <T> Predicate<T> stringSize(final Function<T, String> source, final Function<T, String> target) {
        return Predicate.<T>not(nullValue())
                .and(obj -> not(nullValue()).test(target.apply(obj)))
                .and(obj -> stringSize(target.apply(obj).length()).test(source.apply(obj)));
    }

    public static Predicate<String> stringSizeBetween(final Integer minSize, final Integer maxSize) {
        return Predicate.<String>not(nullValue())
                .and(stringSizeGreaterThanOrEqual(minSize).and(stringSizeLessThanOrEqual(maxSize)));
    }

    public static <T> Predicate<T> stringSizeBetween(final Function<T, String> source, final Integer minSize, final Integer maxSize) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringSizeBetween(minSize, maxSize).test(source.apply(obj)));
    }

    public static Predicate<String> stringSizeGreaterThan(final Integer size) {
        return Predicate.<String>not(nullValue())
                .and(string -> not(nullValue()).test(size))
                .and(string -> greaterThan(size).test(string.length()));
    }

    public static <T> Predicate<T> stringSizeGreaterThan(final Function<T, String> source, final Integer size) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringSizeGreaterThan(size).test(source.apply(obj)));
    }

    public static <T> Predicate<T> stringSizeGreaterThan(final Function<T, String> source, final Function<T, String> target) {
        return Predicate.<T>not(nullValue())
                .and(obj -> not(nullValue()).test(target.apply(obj)))
                .and(obj -> stringSizeGreaterThan(target.apply(obj).length()).test(source.apply(obj)));
    }

    public static Predicate<String> stringSizeGreaterThanOrEqual(final Integer size) {
        return Predicate.<String>not(nullValue())
                .and(stringSizeGreaterThan(size).or(stringSize(size)));
    }

    public static <T> Predicate<T> stringSizeGreaterThanOrEqual(final Function<T, String> source, final Integer size) {
        return Predicate.<T>not(nullValue())
                .and(stringSizeGreaterThan(source, size).or(stringSize(source, size)));
    }

    public static <T> Predicate<T> stringSizeGreaterThanOrEqual(final Function<T, String> source, final Function<T, String> target) {
        return Predicate.<T>not(nullValue())
                .and(stringSizeGreaterThan(source, target).or(stringSize(source, target)));
    }

    public static Predicate<String> stringSizeLessThan(final Integer size) {
        return Predicate.<String>not(nullValue())
                .and(stringSizeLessThan -> not(nullValue()).test(size))
                .and(stringSizeLessThan -> lessThan(size).test(stringSizeLessThan.length()));
    }

    public static <T> Predicate<T> stringSizeLessThan(final Function<T, String> source, final Integer size) {
        return Predicate.<T>not(nullValue())
                .and(obj -> stringSizeLessThan(size).test(source.apply(obj)));
    }

    public static <T> Predicate<T> stringSizeLessThan(final Function<T, String> source, final Function<T, String> target) {
        return Predicate.<T>not(nullValue())
                .and(obj -> not(nullValue()).test(target.apply(obj)))
                .and(obj -> stringSizeLessThan(target.apply(obj).length()).test(source.apply(obj)));
    }

    public static Predicate<String> stringSizeLessThanOrEqual(final Integer size) {
        return Predicate.<String>not(nullValue())
                .and(stringSizeLessThan(size).or(stringSize(size)));
    }

    public static <T> Predicate<T> stringSizeLessThanOrEqual(final Function<T, String> source, final Integer size) {
        return Predicate.<T>not(nullValue())
                .and(stringSizeLessThan(source, size).or(stringSize(source, size)));
    }

    public static <T> Predicate<T> stringSizeLessThanOrEqual(final Function<T, String> source, final Function<T, String> target) {
        return Predicate.<T>not(nullValue())
                .and(stringSizeLessThan(source, target).or(stringSize(source, target)));
    }

    public static <T extends String> Predicate<T> stringInCollection(final Collection<String> collection) {
        return Predicate.<T>not(nullValue())
                .and(obj -> not(nullValue()).test(collection))
                .and(collection::contains);
    }

    public static <T> Predicate<T> stringInCollection(final Function<T, String> source, final Collection<String> collection) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(source)))
                .and(obj -> not(nullValue()).test(collection))
                .and(obj -> stringInCollection(collection).test(source.apply(obj)));
    }

    public static <T> Predicate<T> stringInCollection(final String source, final Function<T, Collection<String>> target) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(target)))
                .and(obj -> stringInCollection(target.apply(obj)).test(source));
    }

    public static <T> Predicate<T> stringInCollection(final Function<T, String> source, final Function<T, Collection<String>> target) {
        return Predicate.<T>not(nullValue())
                .and(not(nullValue(source)))
                .and(not(nullValue(target)))
                .and(obj -> stringInCollection(target.apply(obj)).test(source.apply(obj)));
    }
}
