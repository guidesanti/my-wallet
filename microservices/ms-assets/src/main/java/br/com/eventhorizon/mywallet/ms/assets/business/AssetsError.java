package br.com.eventhorizon.mywallet.ms.assets.business;

import br.com.eventhorizon.common.error.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AssetsError {

    ASSET_ALREADY_EXIST(ErrorCode.app(AssetsError.DOMAIN, "ASSET_ALREADY_EXIST"), "Asset already exist with shortName '%s' or longName '%s'"),
    ASSET_NOT_FOUND(ErrorCode.app(AssetsError.DOMAIN, "ASSET_NOT_FOUND"), "Asset not found for ID '%s'"),
    ASSET_TYPE_ALREADY_EXIST(ErrorCode.app(AssetsError.DOMAIN, "ASSET_TYPE_ALREADY_EXIST"), "Asset type already exist with name '%s'");

    private static final String DOMAIN = "ASSETS";

    private final ErrorCode code;

    private final String messageTemplate;

    public String getMessage(Object ...args) {
        return String.format(messageTemplate, args);
    }
}
