package com.barbatech.natomada.auth.infrastructure.config;

import com.barbatech.natomada.auth.infrastructure.security.JwtAuthenticationFilter;
import com.barbatech.natomada.infrastructure.ratelimit.RateLimitFilter;
import com.barbatech.natomada.infrastructure.security.SecurityHeadersFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    /**
     * Security filter chain configuration
     *
     * Public endpoints (no authentication required):
     * - /api/auth/** - Authentication endpoints
     * - /api/cars/** - Vehicle catalog (public data)
     * - /actuator/health/** - Health checks for load balancers/k8s
     * - /v3/api-docs/**, /swagger-ui/** - API docs (disabled by default, enable with SWAGGER_ENABLED=true)
     *
     * Protected endpoints:
     * - All other endpoints require JWT authentication
     *
     * Security features:
     * - Rate limiting on auth endpoints
     * - CORS with configurable allowed origins
     * - Stateless session management (JWT-based)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Public API endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/cars", "/api/cars/**").permitAll()
                // Health checks for load balancers and Kubernetes probes
                .requestMatchers("/actuator/health", "/actuator/health/**", "/actuator/info").permitAll()
                // Swagger UI - access controlled by springdoc.swagger-ui.enabled property
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            // Security headers filter runs first to add headers to all responses
            .addFilterBefore(securityHeadersFilter, UsernamePasswordAuthenticationFilter.class)
            // Rate limiting filter runs early to block abusive requests
            .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
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
