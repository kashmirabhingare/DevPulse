package com.devpulse.util;

import java.security.SecureRandom;

public class ApiKeyGenerator {

    private static final String PREFIX = "dp_";

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int KEY_LENGTH = 24;

    private static final SecureRandom RANDOM = new SecureRandom();

    private ApiKeyGenerator() {
    }

    public static String generateApiKey() {

        StringBuilder builder = new StringBuilder(PREFIX);

        for (int i = 0; i < KEY_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(index));
        }

        return builder.toString();
    }
}