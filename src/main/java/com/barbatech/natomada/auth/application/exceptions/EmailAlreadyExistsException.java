package com.barbatech.natomada.auth.application.exceptions;

/**
 * Exception thrown when attempting to register with an email that already exists
 */
public class EmailAlreadyExistsException extends AuthException {
    public EmailAlreadyExistsException() {
        super("Email já cadastrado");
    }
}
