package com.barbatech.natomada.auth.application.exceptions;

/**
 * Exception thrown when attempting to register with a phone that already exists
 */
public class PhoneAlreadyExistsException extends AuthException {
    public PhoneAlreadyExistsException() {
        super("Telefone já cadastrado");
    }
}
