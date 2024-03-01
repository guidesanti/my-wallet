package br.com.eventhorizon.mywallet.ms.assets.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Strategy {

    NO_INCOME("Suitable for assets that do not provide any income like checking accounts, investment accounts, etc"),
    VARIABLE_INCOME("Suitable for assets that provide variable income like stocks, FIIs, FIAGROs, ETFs, etc"),
    FIXED_INCOME("Suitable for assets that provide fixed income like LCIs, LCAs, bonds, debentures, etc"),
    HYBRID("Suitable for assets that may provide fixed and variable income like investiment funds, COEs, etc"),
    SAVINGS("Suitable for savings accounts, FGTS accounts, etc"),
    OTHER("Suitable for assets that do not apply for the other strategies"),
    UNKNOWN("If the strategy of the asset is not in list above");

    private final String description;

    public static Strategy of(String name) {
        try {
            return Strategy.valueOf(name);
        } catch (Exception ex) {
            return Strategy.UNKNOWN;
        }
    }
}
