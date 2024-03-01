package br.com.eventhorizon.mywallet.ms.assets.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Income {

    NONE("Suitable for assets that do not have any income"),
    VARIABLE("Suitable for assets of variable income"),
    FIXED("Suitable for assets of fixed income"),
    INDEXED("Suitable for assets of income base on some index like IPCA, SELIC, etc"),
    MIXED("Suitable for assets that mix variable, fixed and indexed income in some way"),
    OTHER("Suitable for assets that do not apply for the other incomes"),
    UNKNOWN("If the income of the asset is not in list above");

    private final String description;

    public static Income of(String name) {
        try {
            return Income.valueOf(name);
        } catch (Exception ex) {
            return Income.UNKNOWN;
        }
    }
}
