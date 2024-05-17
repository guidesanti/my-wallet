package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.refusal.Refusal;

public class BusinessException extends RefusedException {

    public BusinessException(Refusal refusal) {
        this(refusal, null);
    }

    public BusinessException(Refusal refusal, Throwable cause) {
        super(refusal, cause);
    }
}
