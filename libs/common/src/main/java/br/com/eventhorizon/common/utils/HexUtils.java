package br.com.eventhorizon.common.utils;

import java.util.Base64;

public final class HexUtils {

    public static byte[] hexStringToByteArray(String hexString) {
        byte[] byteArray = new byte[hexString.length() / 2];

        for (int i = 0; i < hexString.length() / 2; i++) {
            int index = 2 * i;
            int val = Integer.parseInt(hexString.substring(index, index + 2), 16);
            byteArray[i] = (byte) val;
        }

        return byteArray;
    }

    public static String byteArrayToHexString(byte[] byteArray) {
        int len = byteArray.length;
        char[] hexValues = "0123456789ABCDEF".toCharArray();
        char[] hexChar = new char[2 * len];

        for (int i = 0; i < len; i++) {
            int value = byteArray[i] & 0xFF;
            hexChar[2 * i] = hexValues[value >>> 4];
            hexChar[(2 * i) + 1] = hexValues[value & 0x0F];
        }

        return String.valueOf(hexChar);
    }

    public static String base64StringToHexString(String base64String) {
        var byteArray = Base64.getDecoder().decode(base64String);
        return byteArrayToHexString(byteArray);
    }
}
