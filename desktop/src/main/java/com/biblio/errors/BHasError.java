package com.biblio.errors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class BHasError {

    public static boolean isPassword(String key, String value) {

        if (value == null || value.trim().isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_PASSWORD.get());
            return false;
        }

        // Vérifie la longueur minimale du mot de passe
        if (value.length() < 8) {
            BErrorMgr.addError(key, BErrors.ERROR_PASSWORD.get());
            return false;
        }

        // Vérifie la présence d'au moins une lettre majuscule
        if (!value.matches(".*[A-Z].*")) {
            BErrorMgr.addError(key, BErrors.ERROR_PASSWORD.get());
            return false;
        }

        // Vérifie la présence d'au moins une lettre minuscule
        if (!value.matches(".*[a-z].*")) {
            BErrorMgr.addError(key, BErrors.ERROR_PASSWORD.get());
            return false;
        }

        // Vérifie la présence d'au moins un chiffre
        if (!value.matches(".*\\d.*")) {
            BErrorMgr.addError(key, BErrors.ERROR_PASSWORD.get());
            return false;
        }

        // Vérifie la présence d'au moins un caractère spécial
        if (!value.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            BErrorMgr.addError(key, BErrors.ERROR_PASSWORD.get());
            return false;
        }

        return true;
    }

    public static boolean isGreaterThanZero(String key, String value) {

        if (value == null || value.trim().isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_RESSOURCE_LOW.get());
            return false;
        }

        try {
            double numericValue = Double.parseDouble(value);
            if (numericValue <= 0) {
                BErrorMgr.addError(key, BErrors.ERROR_RESSOURCE_LOW.get());
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            BErrorMgr.addError(key, BErrors.ERROR_NUMERIC.get());
            return false;
        }
    }

    // Vérifie si la valeur est numérique
    public static boolean isNumeric(String key, String value) {

        if (value == null || value.trim().isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_NUMERIC.get());
            return false;
        }

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            BErrorMgr.addError(key, BErrors.ERROR_NUMERIC.get());
            return false;
        }
    }

    public static boolean isInteger(String key, String value) {

        if (value == null || value.trim().isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_INTEGER.get());
            return false;
        }

        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            BErrorMgr.addError(key, BErrors.ERROR_INTEGER.get());
            return false;
        }
    }

    // Vérifie si l'email est valide
    public static boolean isEmail(String key, String value) {

        if (value == null || value.trim().isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_EMAIL.get());
            return false;
        }

        // Vérifie le format de l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!value.matches(emailRegex)) {
            BErrorMgr.addError(key, BErrors.ERROR_EMAIL.get());
            return false;
        }

        return true;
    }

    // Vérifie si la date est valide (AAAA-MM-JJ)
    public static boolean isDateValid(String key, String dateStr) {

        dateStr = dateStr.trim();

        if (dateStr.isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_DATE_INVALID.get());
            return false;
        }
        
        LocalDate date = null;
        try {
            date = java.time.LocalDate.parse(dateStr);
        } catch (Exception e) {
            BErrorMgr.addError(key, BErrors.ERROR_DATE_INVALID.get());
            return false;
        }

        if (date != null) {
            if(date.getYear() < 1900 || date.getYear() > 2100) {
                BErrorMgr.addError(key, BErrors.ERROR_DATE_PAST.get());
                return false;
            }

            if(date.getMonthValue() < 1 || date.getMonthValue() > 12) {
                BErrorMgr.addError(key, BErrors.ERROR_DATE_INVALID.get());
                return false;
            }

            if(date.getDayOfMonth() < 1 || date.getDayOfMonth() > 31) {
                BErrorMgr.addError(key, BErrors.ERROR_DATE_INVALID.get());
                return false;
            }

            if(date.getMonthValue() == 2 && date.getDayOfMonth() > 29) {
                BErrorMgr.addError(key, BErrors.ERROR_DATE_INVALID.get());
                return false;
            }

            if((date.getMonthValue() == 4 || date.getMonthValue() == 6 || date.getMonthValue() == 9 || date.getMonthValue() == 11) && date.getDayOfMonth() > 30) {
                BErrorMgr.addError(key, BErrors.ERROR_DATE_INVALID.get());
                return false;
            }

            if(date.getMonthValue() == 2 && date.getDayOfMonth() == 29) {
                if(!date.isLeapYear()) {
                    BErrorMgr.addError(key, BErrors.ERROR_DATE_INVALID.get());
                    return false;
                }
            }

        }

        return true;
    }

    // Vérifie si l'heure est valide (HH:MM ou HH:MM:SS)
    public static boolean isHour(String key, String value) {

        if (value == null || value.trim().isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_HOUR.get());
            return false;
        }

        try {
            LocalTime.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            BErrorMgr.addError(key, BErrors.ERROR_HOUR.get());
            return false;
        }
    }

    // Vérifie si le numéro de téléphone est valide
    public static boolean isPhone(String key, String value) {

        if (value == null || value.trim().isEmpty()) {
            BErrorMgr.addError(key, BErrors.ERROR_PHONE.get());
            return false;
        }

        // retire espaces et tirets
        value = value.replace(" ", "").replace("-", "");

        // entre 8 et 14 chiffres
        if(value.matches("\\d{8,14}")) {
            return true;
        } else {
            BErrorMgr.addError(key, BErrors.ERROR_PHONE.get());
            return false;
        }
    }
}
