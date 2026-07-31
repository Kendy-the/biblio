package com.biblio.errors;

public enum BErrors {

    ERROR_INVALID_INPUT("Invalid input."),
    ERROR_EMPTY_FIELD("Required field is empty."),

    ERROR_FAILED("Operation failed."),
    ERROR_RECORD("Record not found."),
    ERROR_DB("Database connection failed."),

    ERROR_RESOURCE_EXISTS("Resource already exists."),
    ERROR_RESOURCE_NOT_EXISTS("Resource not exists."),
    ERROR_RESSOURCE_IN_RELATIONSHIP("Ressource has a relationship"),
    ERROR_RESSOURCE_LOW("Ressource is less than 1"),
    ERROR_RESSOURCE_HIGH("Ressource is greater than 1"),

    ERROR_AUTHENTICATION("Authentication failed."),
    ERROR_PERMISSION_DENIED("Permission denied."),
    ERROR_INTERNAL_SERVER_ERROR("Internal server error."),
    ERROR_TIMEOUT("Timeout occurred."),
    
    ERROR_PASSWORD("Password must be at least 8 characters long\n" 
        + "and contain at least one uppercase letter,\n" 
        + "one lowercase letter, one digit,\n" 
        + "and one special character."
    ),
    ERROR_NUMERIC("Value must be numeric."),
    ERROR_INTEGER("Value must be integer."),
    ERROR_EMAIL("Invalid email format."),
    ERROR_PHONE("Invalid phone number. (+509XXXX-XXXX or 509XXXX-XXXX)"),
    ERROR_HOUR("Invalid hour format. (HH:MM or HH:MM:SS)"),
    ERROR_DATE_INVALID("Invalid date format. (YYYY-MM-DD)"),
    ERROR_DATE_PAST("Date cannot be in the past (< 1900)."), 
    ERROR_DATE_BECOME("Date cannot be in the future (> 2100)."), 
    
    ERROR_UNKNOWN("Unknown error occurred.");

    private final String text;

    BErrors(String text) {
        this.text = text;
    }

    public String get() {
        return this.text;
    }
}
