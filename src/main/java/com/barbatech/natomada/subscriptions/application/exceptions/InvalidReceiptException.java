package com.barbatech.natomada.subscriptions.application.exceptions;

/**
 * Exception thrown when a receipt is invalid
 */
public class InvalidReceiptException extends RuntimeException {

    public InvalidReceiptException(String message) {
        super(message);
    }

    public InvalidReceiptException(String message, Throwable cause) {
        super(message, cause);
    }
}
