package br.com.eventhorizon.common.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Optional;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @NonNull
    private final ErrorCode code;

    @NonNull
    private final String message;

    private final String additionalInformation;

    public Optional<String> getAdditionalInformation() {
        return Optional.ofNullable(additionalInformation);
    }

    public static Error of(ErrorCode code, String message) {
        return of(code, message, null);
    }

    public static Error of(ErrorCode code, String message, String additionalInformation) {
        return new Error(code, message, additionalInformation);
    }
}
