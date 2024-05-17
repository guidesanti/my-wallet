package br.com.eventhorizon.common.http;

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
    SUCCESS, // 200, 201, 202
    REFUSED, // 400, 401, 403, 404, 409, 422
    FAILURE; // 500

    /**
     * Returns the StatusCode enum constant based on the given value.
     * If the value doesn't match any StatusCode enum constant, return UNKNOWN.
     *
     * @param value the value to match with a StatusCode enum constant
     * @return the matching StatusCode enum constant, or UNKNOWN if no match found
     */
    public static StatusCode of(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        for (var s : StatusCode.values()) {
            if (s.name().equals(value)) {
                return s;
            }
        }
        return UNKNOWN;
    }

    /**
     * Returns the StatusCode corresponding to the given value.
     * If the value doesn't match any StatusCode enum constant, throws an IllegalArgumentException.
     *
     * @param value the value to match with a StatusCode enum constant
     * @return the matching StatusCode enum constant
     * @throws IllegalArgumentException if the value does not match any StatusCode
     */
    public static StatusCode ofOrThrow(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Null value not allowed");
        }
        for (var s : StatusCode.values()) {
            if (s.name().equals(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status code value '" + value + "'");
    }
}
