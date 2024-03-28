package br.com.eventhorizon.common.error;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ErrorCode {

    private static final String PATTERN = "^[A-Z_]+$";

    private static final String SEPARATOR = ".";

    private final Type type;

    private final String domain;

    private final String code;

    private ErrorCode(Type type, String domain, String code) {
        this.type = type;
        this.domain = domain;
        this.code = code;
        validate();
    }

    public static ErrorCode of(Type type, String domain, String code) {
        return new ErrorCode(type, domain, code);
    }

    public static ErrorCode lib(String domain, String code) {
        return new ErrorCode(Type.LIB, domain, code);
    }

    public static ErrorCode app(String domain, String code) {
        return new ErrorCode(Type.APP, domain, code);
    }

    @Override
    public String toString() {
        return type + SEPARATOR + domain + SEPARATOR + code;
    }

    private void validate() {
        // Type
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        // Domain
        if (domain == null) {
            throw new IllegalArgumentException("Domain cannot be null");
        }
        if (!domain.matches(PATTERN)) {
            throw new IllegalArgumentException("Domain cannot be blank and should match pattern '" + PATTERN + "'");
        }

        // Code
        if (code == null) {
            throw new IllegalArgumentException("Code cannot be null");
        }
        if (!code.matches(PATTERN)) {
            throw new IllegalArgumentException("Code cannot be blank and should match pattern '" + PATTERN + "'");
        }
    }

    public enum Type {
        LIB,
        APP;
    }
}
