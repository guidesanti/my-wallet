package br.com.eventhorizon.common.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Value
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @NonNull
    ErrorCode code;

    @NonNull
    String message;

    String extraDetails;

    public static Error of(ErrorCode code, String message) {
        return new Error(code, message, null);
    }

    public static Error of(ErrorCode code, String message, String extraDetails) {
        return new Error(code, message, extraDetails);
    }
}
