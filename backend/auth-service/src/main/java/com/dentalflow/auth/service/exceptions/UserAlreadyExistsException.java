package com.dentalflow.auth.service.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("Usuario con username '%s' ya existe".formatted(username));
    }
}