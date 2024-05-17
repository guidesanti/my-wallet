package br.com.eventhorizon.common;

import java.time.format.DateTimeFormatter;

public final class Common {

    public static final String PLATFORM_PREFIX_LOWER_CASE = "eh";

    public static final String PLATFORM_PREFIX_UPPER_CASE = "EH";

    public static final String PLATFORM_DOMAIN = "br.com.eventhorizon";

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static final class MDCKey {

        public static final String IDEMPOTENCE_ID = "idempotenceId";

        public static final String TRACE_ID = "traceId";
    }

    public static final class Env {

        public static final String APPLICATION_NAME = PLATFORM_PREFIX_UPPER_CASE + "_APPLICATION_NAME";

        public static final String ENV = PLATFORM_PREFIX_UPPER_CASE + "_ENV";
    }

    public static final class SysProp {

        public static final String APPLICATION_NAME = PLATFORM_DOMAIN + ".service.name";

        public static final String ENV = PLATFORM_DOMAIN + ".service.env";
    }
}
