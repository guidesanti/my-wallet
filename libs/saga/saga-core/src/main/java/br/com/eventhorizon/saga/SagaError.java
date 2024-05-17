package br.com.eventhorizon.saga;

import br.com.eventhorizon.common.refusal.RefusalReasonCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SagaError {

    IDEMPOTENCE_ID_CONFLICT(RefusalReasonCode.lib(SagaError.DOMAIN, "IDEMPOTENCE_ID_CONFLICT"), "Idempotence ID conflict, idempotence ID '%s' already used for a different request"),
    IDEMPOTENCE_ID_NOT_PRESENT(RefusalReasonCode.lib(SagaError.DOMAIN, "IDEMPOTENCE_ID_NOT_PRESENT"), "Idempotence ID not present"),
    IDEMPOTENCE_ID_INVALID(RefusalReasonCode.lib(SagaError.DOMAIN, "IDEMPOTENCE_ID_INVALID"), "Idempotence ID '%s' is invalid");

    private static final String DOMAIN = "SAGA_CORE";

    private final RefusalReasonCode code;

    private final String messageTemplate;

    public String getMessage(Object ...args) {
        return String.format(messageTemplate, args);
    }
}
