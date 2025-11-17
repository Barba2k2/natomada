package com.barbatech.natomada.reviews.application.exceptions;

/**
 * Base exception for review errors
 */
public class ReviewException extends RuntimeException {
    public ReviewException(String message) {
        super(message);
    }

    public ReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}
