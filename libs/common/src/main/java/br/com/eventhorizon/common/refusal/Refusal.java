package br.com.eventhorizon.common.refusal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Refusal {

    @NonNull
    private final RefusalReasonCode code;

    @NonNull
    private final String message;

    private final String additionalInformation;

    public Optional<String> getAdditionalInformation() {
        return Optional.ofNullable(additionalInformation);
    }

    public static Refusal of(RefusalReasonCode code, String message) {
        return of(code, message, null);
    }

    public static Refusal of(RefusalReasonCode code, String message, String additionalInformation) {
        return new Refusal(code, message, additionalInformation);
    }
}
