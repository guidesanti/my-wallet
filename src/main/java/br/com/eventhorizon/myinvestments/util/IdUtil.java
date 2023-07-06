package br.com.eventhorizon.myinvestments.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class IdUtil {

    private static final String MD5 = "MD5";

    public static String generateDocumentId() throws NoSuchAlgorithmException {
        long now = System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance(MD5).digest(Long.toString(now).getBytes()));
    }

    public static String generateDocumentId(String seed) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance(MD5).digest(seed.getBytes()));
    }

}
