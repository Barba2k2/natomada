package com.barbatech.natomada.infrastructure.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Development Email Service - Logs emails to console instead of sending
 * Active in dev profile for local development without SMTP
 */
@Slf4j
@Service
@Profile("dev")
public class LoggingEmailService implements EmailService {

    @Override
    public void sendPasswordResetEmail(String to, String token, String userName) {
        log.info("=== PASSWORD RESET EMAIL ===");
        log.info("To: {}", to);
        log.info("User: {}", userName);
        log.info("OTP Code: {}", token);
        log.info("============================");
    }

    @Override
    public void sendWelcomeEmail(String to, String userName) {
        log.info("=== WELCOME EMAIL ===");
        log.info("To: {}", to);
        log.info("User: {}", userName);
        log.info("=====================");
    }

    @Override
    public void sendEmailVerification(String to, String token, String userName) {
        log.info("=== EMAIL VERIFICATION ===");
        log.info("To: {}", to);
        log.info("User: {}", userName);
        log.info("Token: {}", token);
        log.info("Verification Link: http://localhost:3000/verify-email?token={}", token);
        log.info("==========================");
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("=== GENERIC EMAIL ===");
        log.info("To: {}", to);
        log.info("Subject: {}", subject);
        log.info("Body: {}", body);
        log.info("=====================");
    }
}
