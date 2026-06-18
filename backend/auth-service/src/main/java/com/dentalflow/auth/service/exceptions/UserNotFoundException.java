package com.dentalflow.auth.service.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("No se encontro el usuario con username '%s' ".formatted(username));
    }
}
