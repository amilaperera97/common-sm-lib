package com.techbooker.sm.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static final Random RANDOM = new Random();

    public static boolean isLengthEqualValid(String inputString, int length) {
        return (inputString.length() == length);
    }

    public static boolean hasEmptySpace(String string) {
        Pattern whitespace = Pattern.compile("\\s");
        Matcher matcher = whitespace.matcher(string);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static boolean haveSpecialCharacters(String input) {

        Pattern special = Pattern.compile("[^A-Za-z0-9]+");
        Matcher hasSpecial = special.matcher(input);
        return (hasSpecial.find());
    }

    public static boolean isTraceIdFormat(String inputString) {
        String head = inputString.substring(0, 3);
        String tail = inputString.substring(3, 15);
        if (!isCharacters(head) || !isNumeric(tail)) {
            return false;
        }
        return true;
    }

    public static boolean isCharacters(String input) {
        return input.matches("^[a-zA-Z]*$");
    }

    public static boolean isNumeric(String stringNumber) {
        if (stringNumber == null) {
            return false;
        }
        try {
            Double.parseDouble(stringNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static long getTimeTaken(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    public static String traceIDGenerator(String serviceName) {
        long randomNum = generateRandom();
        return serviceName.toUpperCase().substring(0, 3) + randomNum;
    }

    private static long generateRandom() {
        int length = 12;
        char[] digits = new char[length];
        digits[0] = (char) (RANDOM.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (RANDOM.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
}
