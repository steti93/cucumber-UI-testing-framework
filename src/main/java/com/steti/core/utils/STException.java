package com.steti.core.utils;

public class STException extends RuntimeException {

    public STException(String message) {
        super(message);
    }

    public STException(String message, Exception exc) {
        super(message, exc);
    }
}
