package com.biblio.controllers;

import com.biblio.errors.BHasError;

public class Controller {

    public static boolean isDateValid(String key, String dateStr) {
        return BHasError.isDateValid(key, dateStr);
    }

    public static boolean isHour(String key, String value) {
        return BHasError.isHour(key, value);
    }

    public static boolean isPhone(String key, String value) {
        return BHasError.isPhone(key, value);
    }

    public static boolean isNumeric(String key, String value) {
        return BHasError.isNumeric(key, value);
    }

    public static boolean isEmail(String key, String value) {
        return BHasError.isEmail(key, value);
    }

    public static boolean isInteger(String key, String value) {
        return BHasError.isInteger(key, value);
    }

    public static boolean isGreaterThanZero(String key, String value) {
        return BHasError.isGreaterThanZero(key, value);
    }

    public static boolean isPassword(String key, String value) {
        return BHasError.isPassword(key, value);
    }

}
