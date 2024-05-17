package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.refusal.Refusal;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class RefusedException extends RuntimeException {

    private final Refusal refusal;

    public RefusedException(@NonNull Refusal refusal) {
        this(refusal, null);
    }

    public RefusedException(@NonNull Refusal refusal, Throwable cause) {
        super(refusal.toString(), cause);
        this.refusal = refusal;
    }
}
