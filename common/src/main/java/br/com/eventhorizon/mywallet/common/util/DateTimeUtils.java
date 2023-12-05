package br.com.eventhorizon.mywallet.common.util;

import br.com.eventhorizon.mywallet.common.Conventions;

import java.time.OffsetDateTime;

public final class DateTimeUtils {

    public static OffsetDateTime stringToOffsetDateTime(String dateTime) {
        return dateTime == null ? null : OffsetDateTime.parse(dateTime, Conventions.DEFAULT_DATE_TIME_FORMATTER);
    }

    public static String offsetDateTimeToString(OffsetDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(Conventions.DEFAULT_DATE_TIME_FORMATTER);
    }
}
