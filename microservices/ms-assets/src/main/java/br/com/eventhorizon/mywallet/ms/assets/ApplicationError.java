package br.com.eventhorizon.mywallet.ms.assets;

import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.ErrorCode;
import br.com.eventhorizon.common.exception.BaseErrorException;
import br.com.eventhorizon.common.exception.BusinessErrorException;
import br.com.eventhorizon.common.exception.ServerErrorException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationError {

    private static final String DOMAIN = "ASSETS";

    public static final ApplicationError UNKNOWN_ERROR = new ApplicationError(
            ErrorCode.app(DOMAIN, "UNKNOWN_ERROR"),
            "Unknown error",
            ServerErrorException.class);

    public static final ApplicationError ASSET_ALREADY_EXIST = new ApplicationError(
            ErrorCode.app(DOMAIN, "ASSET_ALREADY_EXIST"),
            "Asset already exist with shortName '%s' or longName '%s'",
            BusinessErrorException.class);

    public static final ApplicationError ASSET_NOT_FOUND = new ApplicationError(
            ErrorCode.app(DOMAIN, "ASSET_NOT_FOUND"),
            "Asset not found for ID '%s'",
            BusinessErrorException.class);

    public static final ApplicationError ASSET_TYPE_ALREADY_EXIST = new ApplicationError(
            ErrorCode.app(DOMAIN, "ASSET_TYPE_ALREADY_EXIST"),
            "Asset type already exist with name '%s'",
            BusinessErrorException.class);

    private final ErrorCode code;

    private final String messageTemplate;

    private final Class<? extends BaseErrorException> exceptionClass;

    public Error build(Object ...messageArgs) {
        return build(null, messageArgs);
    }

    public Error build(String additionalInformation, Object ...messageArgs) {
        return Error.of(code, buildMessage(messageArgs), additionalInformation);
    }

    public void buildAndThrow(Object ...messageArgs) {
        buildAndThrow(null, messageArgs);
    }

    public void buildAndThrow(String additionalInformation, Object ...messageArgs) {
        var error = build(additionalInformation, messageArgs);
        BaseErrorException exception;
        try {
            exception = exceptionClass.getConstructor(Error.class).newInstance(error);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        throw exception;
    }

    private String buildMessage(Object ...messageArgs) {
        try {
            return String.format(messageTemplate, messageArgs);
        } catch (Exception ex) {
            log.error("Error building message, will return the template instead of formated message", ex);
            return messageTemplate;
        }
    }
}
