package com.barbatech.natomada.auth.application.exceptions;

/**
 * Exception thrown when login credentials are invalid
 */
public class InvalidCredentialsException extends AuthException {
    public InvalidCredentialsException() {
        super("Email ou senha incorretos");
    }
}
