package com.barbatech.natomada.auth.application.exceptions;

/**
 * Exception thrown when passwords don't match
 */
public class PasswordMismatchException extends AuthException {
    public PasswordMismatchException() {
        super("As senhas não coincidem");
    }
}
