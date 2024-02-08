package br.com.eventhorizon.common.util;

import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.exception.BaseErrorException;

import java.util.HashMap;
import java.util.Map;

public final class LogUtils {

    private static final Map<ErrorCategory, String> HEAD = new HashMap<>();

    static {
        HEAD.put(ErrorCategory.BUSINESS_ERROR, "Business error: ");
        HEAD.put(ErrorCategory.CLIENT_ERROR, "Client error: ");
        HEAD.put(ErrorCategory.SERVER_ERROR, "Server error: ");
    }

    public static String buildErrorLogMessage(BaseErrorException ex) {
        return HEAD.get(ex.getErrorCategory())
                + "errorCode='" + ex.getError().getCode() + "' "
                + "errorMessage='" + ex.getError().getMessage() + "' "
                + "exceptionMessage='" + ex.getMessage() + "'";
    }
}
