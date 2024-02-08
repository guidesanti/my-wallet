package br.com.eventhorizon.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCategory {

    BUSINESS_ERROR("BUSINESS_ERROR"),
    CLIENT_ERROR("CLIENT_ERROR"),
    SERVER_ERROR("SERVER_ERROR");

    private final String value;

    public static ErrorCategory of(String value) {
        for (var s : ErrorCategory.values()) {
            if (s.value.equals(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid error category value '" + value + "'");
    }
}
