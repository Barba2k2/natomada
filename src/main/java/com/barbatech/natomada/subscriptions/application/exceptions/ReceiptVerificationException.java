package com.barbatech.natomada.subscriptions.application.exceptions;

/**
 * Exception thrown when receipt verification fails
 */
public class ReceiptVerificationException extends RuntimeException {

    public ReceiptVerificationException(String message) {
        super(message);
    }

    public ReceiptVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
