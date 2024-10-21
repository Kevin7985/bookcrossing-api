package ru.ist.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordHash {
    public static String hash(String input) {
        StringBuffer sb = new StringBuffer();

        try {
            byte[] passwordBytes = input.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(passwordBytes);

            for (int i = 0; i < md5Bytes.length; i++) {
                sb.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1,3));
            }
        } catch (Exception ignored) { }

        return sb.toString();
    }
}
