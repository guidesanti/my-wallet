package br.com.eventhorizon.mywallet.ms.assets.business.model.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum IncomeType {

    NONE("Suitable for assets that do not have any income"),
    VARIABLE("Suitable for assets of variable income"),
    FIXED("Suitable for assets of fixed income"),
    INDEXED("Suitable for assets of income base on some index like IPCA, SELIC, etc"),
    MIXED("Suitable for assets that mix variable, fixed and indexed income in some way"),
    OTHER("Suitable for assets that do not apply for the other incomes"),
    UNKNOWN("If the income of the asset is not in list above");

    private final String description;

    public static IncomeType of(String name) {
        try {
            return IncomeType.valueOf(name);
        } catch (Exception ex) {
            return IncomeType.UNKNOWN;
        }
    }
}
