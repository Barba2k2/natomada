package com.barbatech.natomada.infrastructure.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Email Service Implementation
 *
 * Uses JavaMailSender to send emails
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

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
            message.setSubject("Redefinição de Senha - NaTomada");
            message.setText(buildPasswordResetEmailBody(token, userName));

            mailSender.send(message);
            log.info("Password reset email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", to, e);
            throw new RuntimeException("Falha ao enviar email de redefinição de senha", e);
        }
    }

    @Override
    public void sendWelcomeEmail(String to, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Bem-vindo ao NaTomada!");
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
            message.setSubject("Verificação de Email - NaTomada");
            message.setText(buildEmailVerificationBody(token, userName));

            mailSender.send(message);
            log.info("Email verification sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email verification to: {}", to, e);
            throw new RuntimeException("Falha ao enviar email de verificação", e);
        }
    }

    private String buildPasswordResetEmailBody(String token, String userName) {
        String resetUrl = frontendUrl + "/reset-password?token=" + token;

        return String.format("""
            Olá %s,

            Recebemos uma solicitação para redefinir sua senha no NaTomada.

            Para redefinir sua senha, clique no link abaixo:
            %s

            Este link expira em 1 hora.

            Se você não solicitou a redefinição de senha, ignore este email.
            Sua senha permanecerá inalterada.

            Atenciosamente,
            Equipe NaTomada
            """, userName, resetUrl);
    }

    private String buildWelcomeEmailBody(String userName) {
        return String.format("""
            Olá %s,

            Bem-vindo ao NaTomada! 🚗⚡

            Estamos felizes em ter você conosco. Com o NaTomada, você pode:

            • Encontrar estações de recarga próximas
            • Salvar suas estações favoritas
            • Gerenciar seus veículos elétricos
            • E muito mais!

            Comece explorando o aplicativo agora: %s

            Atenciosamente,
            Equipe NaTomada
            """, userName, frontendUrl);
    }

    private String buildEmailVerificationBody(String token, String userName) {
        String verificationUrl = frontendUrl + "/verify-email?token=" + token;

        return String.format("""
            Olá %s,

            Obrigado por se cadastrar no NaTomada!

            Para verificar seu email, clique no link abaixo:
            %s

            Este link expira em 24 horas.

            Atenciosamente,
            Equipe NaTomada
            """, userName, verificationUrl);
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
            throw new RuntimeException("Falha ao enviar email", e);
        }
    }
}
