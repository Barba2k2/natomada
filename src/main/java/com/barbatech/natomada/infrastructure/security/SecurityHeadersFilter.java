package com.barbatech.natomada.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that adds security headers to all HTTP responses.
 *
 * Headers added:
 * - X-Content-Type-Options: nosniff - Prevents MIME type sniffing
 * - X-Frame-Options: DENY - Prevents clickjacking by disabling iframes
 * - X-XSS-Protection: 0 - Disabled per modern security recommendations (CSP is preferred)
 * - Referrer-Policy: strict-origin-when-cross-origin - Controls referrer information
 * - Permissions-Policy: Restricts browser features
 * - Strict-Transport-Security: Forces HTTPS (only when enabled)
 * - Content-Security-Policy: Restricts resource loading (configurable)
 */
@Component
public class SecurityHeadersFilter extends OncePerRequestFilter {

    @Value("${security.headers.hsts.enabled:false}")
    private boolean hstsEnabled;

    @Value("${security.headers.hsts.max-age:31536000}")
    private long hstsMaxAge;

    @Value("${security.headers.hsts.include-subdomains:true}")
    private boolean hstsIncludeSubdomains;

    @Value("${security.headers.csp.enabled:false}")
    private boolean cspEnabled;

    @Value("${security.headers.csp.policy:default-src 'self'}")
    private String cspPolicy;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        // Prevent MIME type sniffing
        response.setHeader("X-Content-Type-Options", "nosniff");

        // Prevent clickjacking - deny all framing
        response.setHeader("X-Frame-Options", "DENY");

        // Disable XSS filter (modern recommendation - rely on CSP instead)
        response.setHeader("X-XSS-Protection", "0");

        // Control referrer information sent with requests
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        // Restrict browser features
        response.setHeader("Permissions-Policy",
            "accelerometer=(), camera=(), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), payment=(), usb=()");

        // Cache control for API responses
        if (request.getRequestURI().startsWith("/api/")) {
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            response.setHeader("Pragma", "no-cache");
        }

        // HSTS - only enable when running over HTTPS in production
        if (hstsEnabled) {
            StringBuilder hsts = new StringBuilder();
            hsts.append("max-age=").append(hstsMaxAge);
            if (hstsIncludeSubdomains) {
                hsts.append("; includeSubDomains");
            }
            response.setHeader("Strict-Transport-Security", hsts.toString());
        }

        // Content Security Policy - configurable based on application needs
        if (cspEnabled) {
            response.setHeader("Content-Security-Policy", cspPolicy);
        }

        filterChain.doFilter(request, response);
    }
}
