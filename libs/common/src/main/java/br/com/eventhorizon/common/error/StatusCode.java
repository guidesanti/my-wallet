package br.com.eventhorizon.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The StatusCode class represents different status codes.
 * These codes can be used to indicate the status or result of an operation, for example:
 * - Sync API responses (HTTP REST, gRPC, etc)
 * - Async API reply messages (AMQP, Kafka, SQS, etc)
 * - Any other king of operation that has a result
 */
@Getter
@RequiredArgsConstructor
public enum StatusCode {

    UNKNOWN,
    SUCCESS,
    ERROR;

    /**
     * Returns the StatusCode corresponding to the given value.
     *
     * @param value the value to look for in the StatusCode enum
     * @return the StatusCode associated with the given value
     * @throws IllegalArgumentException if the value does not match any StatusCode
     */
    public static StatusCode of(String value) {
        return of(value, true);
    }

    /**
     * Returns the StatusCode enum constant based on the given value.
     * If strict is set to true, it throws an IllegalArgumentException
     * if the value doesn't match any StatusCode enum constant.
     *
     * @param value  the value to match with a StatusCode enum constant
     * @param strict whether to throw an exception for unmatched value
     * @return the matching StatusCode enum constant, or UNKNOWN if no match found (when strict is false)
     * @throws IllegalArgumentException if strict is true and the value doesn't match any StatusCode enum constant
     */
    public static StatusCode of(String value, boolean strict) {
        if (value == null) {
            if (strict) {
                throw new IllegalArgumentException("Null value not allowed in strict mode");
            } else {
                return UNKNOWN;
            }
        }
        for (var s : StatusCode.values()) {
            if (s.name().equals(value)) {
                return s;
            }
        }
        if (strict) {
            throw new IllegalArgumentException("Invalid status code value '" + value + "'");
        }
        return UNKNOWN;
    }
}
