package br.com.eventhorizon.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This enum represents the category of an error. It provides a set of predefined error categories
 * including {@link #UNKNOWN}, {@link #BUSINESS_ERROR}, {@link #CLIENT_ERROR}, and {@link #SERVER_ERROR}.
 *
 * This enum also provides static methods to convert a string value to an error category using the
 * {@link #of(String)} method. Additionally, it allows toggling strict mode where null values are
 * not allowed using the {@link #of(String, boolean)} method.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCategory {

    UNKNOWN,
    BUSINESS_ERROR,
    CLIENT_ERROR,
    SERVER_ERROR;

    /**
     * Returns the ErrorCategory based on the given value.
     * The value will be used to determine the ErrorCategory with the help of a predefined mapping.
     *
     * @param value the value used to determine the ErrorCategory
     * @return the ErrorCategory object
     */
    public static ErrorCategory of(String value) {
        return of(value, true);
    }

    /**
     * Returns an ErrorCategory based on the given value and strict mode.
     *
     * @param value  the string value representing the error category
     * @param strict a boolean indicating whether strict mode is enabled
     * @return the ErrorCategory based on the given value and strict mode
     * @throws IllegalArgumentException if the value is null and strict mode is enabled,
     *                                  or if the value is invalid and strict mode is enabled
     */
    public static ErrorCategory of(String value, boolean strict) {
        if (value == null) {
            if (strict) {
                throw new IllegalArgumentException("Null value not allowed in strict mode");
            } else {
                return UNKNOWN;
            }
        }
        for (var s : ErrorCategory.values()) {
            if (s.name().equals(value)) {
                return s;
            }
        }
        if (strict) {
            throw new IllegalArgumentException("Invalid error category value '" + value + "'");
        }
        return UNKNOWN;
    }
}
