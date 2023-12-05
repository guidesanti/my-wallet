package br.com.eventhorizon.mywallet.ms.transactions.business;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Errors {

    INVALID_CREATE_TRANSACTION_REQUEST("INVALID_CREATE_TRANSACTION_REQUEST", "Invalid create transaction request");

    private final String code;

    private final String messageTemplate;
}
