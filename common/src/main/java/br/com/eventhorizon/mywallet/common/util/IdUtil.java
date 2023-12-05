package br.com.eventhorizon.mywallet.common.util;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public final class IdUtil {

    private static final String MD5 = "MD5";

    public static String generateId(IdType idType)  {
        if (Objects.requireNonNull(idType) == IdType.MD5_BASE64) {
            return generateMd5Base64Id();
        }
        return generateUuidId();
    }

    private static String generateUuidId() {
        return UUID.randomUUID().toString();
    }

    private static String generateMd5Base64Id() {
        try {
            long now = System.currentTimeMillis();
            return Base64.getEncoder().encodeToString(MessageDigest.getInstance(MD5).digest(Long.toString(now).getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public enum IdType {
        UUID,
        MD5_BASE64;
    }
}
