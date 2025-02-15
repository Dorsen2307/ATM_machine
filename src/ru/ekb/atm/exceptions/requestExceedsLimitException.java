package ru.ekb.atm.exceptions;

public class requestExceedsLimitException extends RuntimeException {
    public requestExceedsLimitException(String message) {
        super(message);
    }
}
