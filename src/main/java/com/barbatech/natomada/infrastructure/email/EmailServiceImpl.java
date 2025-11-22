package com.barbatech.natomada.infrastructure.email;

import com.barbatech.natomada.infrastructure.i18n.MessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Email Service Implementation
 *
 * Uses JavaMailSender to send emails via SMTP
 * Only active in production/docker profiles
 */
@Slf4j
@Service
@Profile({"prod", "docker"})
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MessageSourceService messageService;

    @Value("${app.email.from:noreply@natomada.com}")
    private String fromEmail;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Override
    public void sendPasswordResetEmail(String to, String token, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(messageService.getMessage("email.password.reset.subject"));
            message.setText(buildPasswordResetEmailBody(token, userName));

            mailSender.send(message);
            log.info("Password reset email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", to, e);
            throw new RuntimeException(messageService.getMessage("email.password.reset.failed"), e);
        }
    }

    @Override
    public void sendWelcomeEmail(String to, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(messageService.getMessage("email.welcome.subject"));
            message.setText(buildWelcomeEmailBody(userName));

            mailSender.send(message);
            log.info("Welcome email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send welcome email to: {}", to, e);
            // Don't throw exception for welcome email - it's not critical
        }
    }

    @Override
    public void sendEmailVerification(String to, String token, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(messageService.getMessage("email.verification.subject"));
            message.setText(buildEmailVerificationBody(token, userName));

            mailSender.send(message);
            log.info("Email verification sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email verification to: {}", to, e);
            throw new RuntimeException(messageService.getMessage("email.verification.failed"), e);
        }
    }

    private String buildPasswordResetEmailBody(String token, String userName) {
        String greeting = messageService.getMessage("email.password.reset.greeting", userName);
        String body = messageService.getMessage("email.password.reset.body");
        String codeLabel = messageService.getMessage("email.password.reset.code.label");
        String expiry = messageService.getMessage("email.password.reset.expiry");
        String ignore = messageService.getMessage("email.password.reset.ignore");
        String signature = messageService.getMessage("email.password.reset.signature");

        return String.format("""
            %s

            %s

            %s

            %s

            %s

            %s

            %s
            """, greeting, body, codeLabel, token, expiry, ignore, signature);
    }

    private String buildWelcomeEmailBody(String userName) {
        String greeting = messageService.getMessage("email.welcome.greeting", userName);
        String body = messageService.getMessage("email.welcome.body");
        String cta = messageService.getMessage("email.welcome.cta", frontendUrl);
        String signature = messageService.getMessage("email.welcome.signature");

        return String.format("""
            %s

            %s

            %s

            %s
            """, greeting, body, cta, signature);
    }

    private String buildEmailVerificationBody(String token, String userName) {
        String greeting = messageService.getMessage("email.verification.greeting", userName);
        String body = messageService.getMessage("email.verification.body");
        String codeLabel = messageService.getMessage("email.verification.code.label");
        String expiry = messageService.getMessage("email.verification.expiry");
        String signature = messageService.getMessage("email.verification.signature");

        return String.format("""
            %s

            %s

            %s

            %s

            %s

            %s
            """, greeting, body, codeLabel, token, expiry, signature);
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            throw new RuntimeException(messageService.getMessage("email.send.failed"), e);
        }
    }
}
