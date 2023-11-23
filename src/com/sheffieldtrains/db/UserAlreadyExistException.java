package com.sheffieldtrains.db;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message)  {
        super(message);
    }
}
