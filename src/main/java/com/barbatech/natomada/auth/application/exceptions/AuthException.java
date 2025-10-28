package com.barbatech.natomada.auth.application.exceptions;

/**
 * Base exception for authentication errors
 */
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
