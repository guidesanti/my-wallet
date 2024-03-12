package br.com.eventhorizon.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtils {

    public static String getStackTraceAsString(Throwable throwable) {
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
