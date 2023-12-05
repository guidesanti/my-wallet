package br.com.eventhorizon.mywallet.common.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @NonNull
    String category;

    @NonNull
    String code;

    @NonNull
    String message;

    String extraDetails;

    public static Error of(String category, String code, String message) {
        return new Error(category, code, message, null);
    }

    public static Error of(String category, String code, String message, String extraDetails) {
        return new Error(category, code, message, extraDetails);
    }
}
