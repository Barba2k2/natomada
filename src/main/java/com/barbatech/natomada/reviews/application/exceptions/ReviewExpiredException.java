package com.barbatech.natomada.reviews.application.exceptions;

/**
 * Exception thrown when attempting to edit/delete a review older than 7 days
 */
public class ReviewExpiredException extends ReviewException {
    public ReviewExpiredException(String message) {
        super(message);
    }
}
