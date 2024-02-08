package br.com.eventhorizon.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private final String value;

    public static StatusCode of(String value) {
        for (var s : StatusCode.values()) {
            if (s.value.equals(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status code value '" + value + "'");
    }
}
