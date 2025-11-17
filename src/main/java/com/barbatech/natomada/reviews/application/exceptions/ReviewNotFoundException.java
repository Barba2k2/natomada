package com.barbatech.natomada.reviews.application.exceptions;

/**
 * Exception thrown when a review is not found
 */
public class ReviewNotFoundException extends ReviewException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
