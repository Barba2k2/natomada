package com.barbatech.natomada.auth.application.exceptions;

/**
 * Exception thrown when a token is invalid or expired
 */
public class InvalidTokenException extends AuthException {
    public InvalidTokenException() {
        super("Token inválido ou expirado");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
