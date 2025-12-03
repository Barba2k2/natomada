package com.barbatech.natomada.auth.infrastructure.config;

import com.barbatech.natomada.auth.infrastructure.security.JwtAuthenticationFilter;
import com.barbatech.natomada.infrastructure.ratelimit.RateLimitFilter;
import com.barbatech.natomada.infrastructure.security.SecurityHeadersFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security Configuration
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RateLimitFilter rateLimitFilter;
    private final SecurityHeadersFilter securityHeadersFilter;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${management.endpoints.metrics.username:metrics}")
    private String metricsUsername;

    @Value("${management.endpoints.metrics.password:changeme}")
    private String metricsPassword;

    /**
     * Security filter chain for metrics endpoints ONLY
     * Uses Basic Authentication for Prometheus/Grafana access
     * Must be @Order(1) to be evaluated before the main API filter chain
     */
    @Bean
    @Order(1)
    public SecurityFilterChain metricsSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(
                request -> {
                    String path = request.getRequestURI();
                    // Only match actuator metrics endpoints, NEVER /api/** endpoints
                    return (path.equals("/actuator/prometheus") ||
                           path.equals("/actuator/metrics") ||
                           path.startsWith("/actuator/metrics/"));
                }
            )
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(basic -> basic.realmName("Metrics"))
            .authorizeHttpRequests(auth -> auth
                .anyRequest().hasRole("METRICS")
            );

        return http.build();
    }

    /**
     * Main security filter chain for API endpoints
     * Uses JWT authentication for all API endpoints
     *
     * Public endpoints (no authentication required):
     * - /api/auth/login, /api/auth/register, /api/auth/refresh
     * - /api/auth/forgot-password, /api/auth/reset-password
     * - /api/auth/send-otp, /api/auth/verify-otp
     * - /actuator/health/** - Health checks for load balancers/k8s
     * - /v3/api-docs/**, /swagger-ui/** - API docs
     *
     * Protected endpoints (require JWT authentication):
     * - /api/auth/logout, /api/auth/me
     * - All other /api/** endpoints
     *
     * Security features:
     * - Rate limiting on auth endpoints
     * - CORS with configurable allowed origins
     * - Stateless session management (JWT-based)
     */
    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(
                request -> {
                    String path = request.getRequestURI();
                    // Match all /api/** endpoints and public paths (health, swagger)
                    return path.startsWith("/api/") ||
                           path.startsWith("/actuator/health") ||
                           path.startsWith("/actuator/info") ||
                           path.startsWith("/v3/api-docs") ||
                           path.startsWith("/swagger-ui");
                }
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)  // Disable Basic Auth for API endpoints
            .authorizeHttpRequests(auth -> auth
                // Public auth endpoints (no authentication required)
                .requestMatchers(
                    "/api/auth/login",
                    "/api/auth/register",
                    "/api/auth/refresh",
                    "/api/auth/forgot-password",
                    "/api/auth/validate-reset-token",
                    "/api/auth/reset-password",
                    "/api/auth/send-otp",
                    "/api/auth/verify-otp"
                ).permitAll()
                // Protected auth endpoints (require JWT authentication)
                .requestMatchers("/api/auth/logout", "/api/auth/me").authenticated()
                // Health checks for load balancers and Kubernetes probes
                .requestMatchers("/actuator/health", "/actuator/health/**", "/actuator/info").permitAll()
                // Swagger UI - access controlled by springdoc.swagger-ui.enabled property
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // All other endpoints require JWT authentication
                .anyRequest().authenticated()
            )
            // Security headers filter runs first to add headers to all responses
            .addFilterBefore(securityHeadersFilter, UsernamePasswordAuthenticationFilter.class)
            // Rate limiting filter runs early to block abusive requests
            .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
            // JWT authentication filter for API endpoints
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService metricsUserDetailsService() {
        org.springframework.security.core.userdetails.User.UserBuilder users =
            org.springframework.security.core.userdetails.User.builder();

        org.springframework.security.core.userdetails.UserDetails metricsUser = users
            .username(metricsUsername)
            .password(passwordEncoder().encode(metricsPassword))
            .roles("METRICS")
            .build();

        return new org.springframework.security.provisioning.InMemoryUserDetailsManager(metricsUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // SECURITY: Use configured allowed origins instead of wildcard
        // For mobile apps, add specific origins or use allowedOriginPatterns for development
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        configuration.setAllowedOrigins(origins);

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "Accept-Language",
            "X-Requested-With"
        ));
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
