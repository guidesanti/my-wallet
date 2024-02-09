package br.com.eventhorizon.common.utils;

import br.com.eventhorizon.common.Conventions;

import java.time.OffsetDateTime;

public final class DateTimeUtils {

    /**
     * Converts a datetime in String format to OffsetDateTime format.
     *
     * @param dateTime The datetime in String format
     * @return The datetime in OffsetDateTime
     * @deprecated Use {@link br.com.eventhorizon.common.conversion.impl.StringToOffsetDateTimeConverter} instead.
     */
    @Deprecated
    public static OffsetDateTime stringToOffsetDateTime(String dateTime) {
        return dateTime == null ? null : OffsetDateTime.parse(dateTime, Conventions.DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * Converts a datetime in OffsetDateTime format to String format.
     *
     * @param dateTime The datetime in OffsetDateTime format
     * @return The datetime in String format
     * @deprecated Use {@link br.com.eventhorizon.common.conversion.impl.OffsetDateTimeToStringConverter} instead.
     */
    @Deprecated
    public static String offsetDateTimeToString(OffsetDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(Conventions.DEFAULT_DATE_TIME_FORMATTER);
    }
}
