package ru.ist.utils;

import java.util.Random;

public class StringGenerator {
    private static final String ALPHABET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
    private static final Integer TOKEN_SIZE = 100;

    public static String generateToken() {
        Random rand = new Random();
        StringBuilder out = new StringBuilder("bookcrossing.");

        for (int i = 0; i < TOKEN_SIZE; i++) {
            out.append(ALPHABET.charAt(rand.nextInt(ALPHABET.length())));
        }

        return out.toString();
    }
}
