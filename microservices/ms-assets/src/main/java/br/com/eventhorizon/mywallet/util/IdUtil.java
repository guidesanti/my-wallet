package br.com.eventhorizon.mywallet.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public final class IdUtil {

    private static final String MD5 = "MD5";

    public static String generateDocumentId() {
//        long now = System.currentTimeMillis();
//        return Base64.getEncoder().encodeToString(MessageDigest.getInstance(MD5).digest(Long.toString(now).getBytes()));
        return UUID.randomUUID().toString();
    }

    public static String generateDocumentId(String seed) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance(MD5).digest(seed.getBytes()));
    }
}
