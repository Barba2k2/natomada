package com.barbatech.natomada.auth.application.exceptions;

import com.barbatech.natomada.reviews.application.exceptions.ReviewExpiredException;
import com.barbatech.natomada.reviews.application.exceptions.ReviewNotFoundException;
import com.barbatech.natomada.subscriptions.application.exceptions.InvalidReceiptException;
import com.barbatech.natomada.subscriptions.application.exceptions.ReceiptVerificationException;
import lombok.extern.slf4j.Slf4j;                                                                                 
import org.springframework.context.MessageSource;                                                                 
import org.springframework.web.multipart.MaxUploadSizeExceededException;                                          
import org.springframework.web.multipart.MultipartException;                 
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Global exception handler for REST controllers
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Obtém o locale atual do contexto
     */
    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * Obtém mensagem traduzida pelo código
     */
    private String getMessage(String code) {
        return messageSource.getMessage(code, null, code, getLocale());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        String message = getMessage("auth.email.already.exists");
        return buildErrorResponse(HttpStatus.CONFLICT, message);
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhoneAlreadyExists(PhoneAlreadyExistsException ex) {
        String message = getMessage("auth.phone.already.exists");
        return buildErrorResponse(HttpStatus.CONFLICT, message);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        String message = getMessage("auth.invalid.credentials");
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, message);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException ex) {
        String message = getMessage("auth.invalid.token");
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, message);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        String message = getMessage("auth.user.not.found");
        return buildErrorResponse(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException ex) {
        String message = getMessage("auth.password.mismatch");
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String errorTitle = getMessage("validation.error");
        String errorMessage = getMessage("validation.invalid.data");

        ValidationErrorResponse response = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .error(errorTitle)
            .message(errorMessage)
            .errors(errors)
            .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
        String message = getMessage("auth.unauthorized");
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ReviewExpiredException.class)
    public ResponseEntity<ErrorResponse> handleReviewExpired(ReviewExpiredException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFound(ReviewNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidReceiptException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReceipt(InvalidReceiptException ex) {
        String message = getMessage("subscription.invalid.receipt");
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ReceiptVerificationException.class)
    public ResponseEntity<ErrorResponse> handleReceiptVerification(ReceiptVerificationException ex) {
        String message = getMessage("subscription.verification.failed");
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        log.warn("File upload exceeded size limit: {}", ex.getMessage());
        String message = getMessage("file.too.large");
        return buildErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE, message);
    }

    @ExceptionHandler(MultipartException.class)                                                                             
    public ResponseEntity<ErrorResponse> handleMultipartException(MultipartException ex) {
        log.warn("Multipart request failed: {}", ex.getMessage());
        String message = getMessage("file.upload.interrupted");
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .error(status.getReasonPhrase())
            .message(message)
            .build();

        return ResponseEntity.status(status).body(response);
    }

    // Error response DTOs
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private Integer status;
        private String error;
        private String message;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ValidationErrorResponse {
        private LocalDateTime timestamp;
        private Integer status;
        private String error;
        private String message;
        private Map<String, String> errors;
    }
}
