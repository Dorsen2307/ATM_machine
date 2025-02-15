package ru.ekb.atm.exceptions;

public class absenceBanknotesException extends RuntimeException {
    public absenceBanknotesException(String message) {
        super(message);
    }
}
