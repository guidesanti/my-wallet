package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.refusal.Refusal;
import lombok.NonNull;

public class ClientException extends RefusedException {

    public ClientException(@NonNull Refusal refusal) {
        this(refusal, null);
    }

    public ClientException(@NonNull Refusal refusal, Throwable cause) {
        super(refusal, cause);
    }
}
