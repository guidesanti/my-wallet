package br.com.eventhorizon.mywallet.ms.assets.business;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Errors {

    TEST("TEST", "Test");

    private final String code;

    private final String messageTemplate;
}
