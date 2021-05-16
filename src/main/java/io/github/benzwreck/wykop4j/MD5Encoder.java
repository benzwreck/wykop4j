package io.github.benzwreck.wykop4j;

import java.security.MessageDigest;

class MD5Encoder {
    static String encode(String data) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] digest = messageDigest.digest();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
