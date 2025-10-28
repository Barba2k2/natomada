package com.barbatech.natomada.auth.application.exceptions;

/**
 * Exception thrown when a user is not found
 */
public class UserNotFoundException extends AuthException {
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
