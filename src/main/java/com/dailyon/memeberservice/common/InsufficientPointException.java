package com.dailyon.memeberservice.common;

public class InsufficientPointException extends RuntimeException {
    public InsufficientPointException(String message) {
        super(message);
    }
}

