package com.barbatech.natomada.infrastructure.email;

/**
 * Email Service Interface
 *
 * Handles sending emails for various application needs
 */
public interface EmailService {

    /**
     * Send password reset email
     *
     * @param to Recipient email address
     * @param token Password reset token
     * @param userName User's name
     */
    void sendPasswordResetEmail(String to, String token, String userName);

    /**
     * Send welcome email
     *
     * @param to Recipient email address
     * @param userName User's name
     */
    void sendWelcomeEmail(String to, String userName);

    /**
     * Send email verification
     *
     * @param to Recipient email address
     * @param token Verification token
     * @param userName User's name
     */
    void sendEmailVerification(String to, String token, String userName);

    /**
     * Send generic email
     *
     * @param to Recipient email address
     * @param subject Email subject
     * @param body Email body
     */
    void sendEmail(String to, String subject, String body);
}
