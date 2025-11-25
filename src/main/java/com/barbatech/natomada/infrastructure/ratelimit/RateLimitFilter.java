package com.barbatech.natomada.infrastructure.ratelimit;

import com.barbatech.natomada.infrastructure.config.RateLimitProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filter that applies rate limiting to authentication endpoints.
 *
 * Rate limits applied:
 * - /api/auth/login: Strict limits (5 per 15 min) to prevent brute force
 * - /api/auth/otp/send: Moderate limits (3 per hour) to prevent SMS bombing
 * - /api/auth/forgot-password: Uses OTP limits
 * - /api/auth/*: General limits (20 per minute) for other auth endpoints
 *
 * SECURITY: X-Forwarded-For headers are only trusted from configured trusted proxies.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    private final RateLimitProperties rateLimitProperties;
    private final ObjectMapper objectMapper;

    private List<CidrRange> trustedProxyRanges;

    @PostConstruct
    public void init() {
        trustedProxyRanges = new ArrayList<>();
        for (String proxy : rateLimitProperties.getTrustedProxies()) {
            try {
                trustedProxyRanges.add(CidrRange.parse(proxy));
            } catch (Exception e) {
                log.warn("Invalid trusted proxy configuration: {}. Skipping.", proxy);
            }
        }
        if (trustedProxyRanges.isEmpty()) {
            log.info("No trusted proxies configured. X-Forwarded-For headers will be ignored.");
        } else {
            log.info("Configured {} trusted proxy range(s) for X-Forwarded-For validation", trustedProxyRanges.size());
        }
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // Only apply rate limiting to POST requests on auth endpoints
        if (!path.startsWith("/api/auth") || !"POST".equals(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getClientIp(request);
        RateLimitResult result;

        // Apply different rate limits based on endpoint
        if (path.equals("/api/auth/login")) {
            result = rateLimitService.checkLoginRateLimit(clientIp);
            if (!result.isAllowed()) {
                log.warn("Login rate limit exceeded for IP: {}", clientIp);
                sendRateLimitResponse(response, result, "Too many login attempts. Please try again later.");
                return;
            }
        } else if (path.equals("/api/auth/otp/send") || path.equals("/api/auth/forgot-password")) {
            // For OTP and password reset, rate limit by the identifier in the request body
            // We use IP for initial check, but ideally should parse request body
            result = rateLimitService.checkOtpRateLimit(clientIp);
            if (!result.isAllowed()) {
                log.warn("OTP rate limit exceeded for IP: {}", clientIp);
                sendRateLimitResponse(response, result, "Too many OTP requests. Please try again later.");
                return;
            }
        } else {
            // General auth rate limit for other endpoints
            result = rateLimitService.checkAuthRateLimit(clientIp);
            if (!result.isAllowed()) {
                log.warn("Auth rate limit exceeded for IP: {}", clientIp);
                sendRateLimitResponse(response, result, "Too many requests. Please try again later.");
                return;
            }
        }

        // Add rate limit headers to response
        response.setHeader("X-RateLimit-Remaining", String.valueOf(result.getRemainingTokens()));

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();

        // Only trust forwarded headers if request comes from a trusted proxy
        if (!isTrustedProxy(remoteAddr)) {
            return remoteAddr;
        }

        // Check for forwarded IP (from load balancer/proxy)
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            // Take the first IP in the chain (original client)
            String clientIp = forwardedFor.split(",")[0].trim();
            log.debug("Trusted proxy {} forwarded request from client {}", remoteAddr, clientIp);
            return clientIp;
        }

        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isEmpty()) {
            log.debug("Trusted proxy {} forwarded X-Real-IP {}", remoteAddr, realIp);
            return realIp;
        }

        return remoteAddr;
    }

    private boolean isTrustedProxy(String ipAddress) {
        if (trustedProxyRanges == null || trustedProxyRanges.isEmpty()) {
            return false;
        }

        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            for (CidrRange range : trustedProxyRanges) {
                if (range.contains(address)) {
                    return true;
                }
            }
        } catch (UnknownHostException e) {
            log.warn("Failed to parse IP address for trusted proxy check: {}", ipAddress);
        }

        return false;
    }

    /**
     * CIDR range representation for trusted proxy validation.
     */
    private static class CidrRange {
        private final byte[] networkAddress;
        private final int prefixLength;

        private CidrRange(byte[] networkAddress, int prefixLength) {
            this.networkAddress = networkAddress;
            this.prefixLength = prefixLength;
        }

        public static CidrRange parse(String cidr) throws UnknownHostException {
            String[] parts = cidr.split("/");
            InetAddress address = InetAddress.getByName(parts[0]);
            int prefix = parts.length > 1 ? Integer.parseInt(parts[1]) : (address.getAddress().length * 8);
            return new CidrRange(address.getAddress(), prefix);
        }

        public boolean contains(InetAddress address) {
            byte[] addr = address.getAddress();
            if (addr.length != networkAddress.length) {
                return false;
            }

            int fullBytes = prefixLength / 8;
            int remainingBits = prefixLength % 8;

            for (int i = 0; i < fullBytes; i++) {
                if (addr[i] != networkAddress[i]) {
                    return false;
                }
            }

            if (remainingBits > 0 && fullBytes < addr.length) {
                int mask = (0xFF << (8 - remainingBits)) & 0xFF;
                if ((addr[fullBytes] & mask) != (networkAddress[fullBytes] & mask)) {
                    return false;
                }
            }

            return true;
        }
    }

    private void sendRateLimitResponse(
        HttpServletResponse response,
        RateLimitResult result,
        String message
    ) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Retry-After", String.valueOf(result.getRetryAfterSeconds()));
        response.setHeader("X-RateLimit-Remaining", "0");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "rate_limit_exceeded");
        errorResponse.put("message", message);
        errorResponse.put("retryAfterSeconds", result.getRetryAfterSeconds());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
