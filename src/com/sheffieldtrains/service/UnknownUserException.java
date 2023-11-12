package com.sheffieldtrains.service;

public class UnknownUserException extends RuntimeException {
    public UnknownUserException(String errorMessage) {
        super(errorMessage);
    }
}
