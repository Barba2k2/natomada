# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

---

## Project Overview

**Na Tomada API** is a Spring Boot backend for EV charging station management in Brazil. Built with Java 21, Spring Boot 3.3.5, using **Layered Architecture** (DDD-lite) with domain-driven design principles.

---

## Development Commands

### Local Development (without Docker)
```bash
# Build project
./gradlew build

# Run application
./gradlew bootRun

# Run tests
./gradlew test

# Clean build
./gradlew clean build

# Skip tests during build
./gradlew build -x test
```

### Docker Development
```bash
# Start all services (Postgres, Redis, App)
docker compose up -d

# Rebuild and start
docker compose up -d --build

# View logs
docker compose logs -f app

# Stop all services
docker compose down

# Stop and remove volumes
docker compose down -v
```

### Database Migrations
```bash
# Migrations run automatically on startup via Flyway
# Location: src/main/resources/db/migration/

# Migration naming: V{number}__{description}.sql
# Example: V14__add_user_preferences.sql
```

---

## Architecture Overview

### Layered Architecture (DDD-lite)

Each feature module follows a **three-layer structure**:

```
src/main/java/com/barbatech/natomada/
├── {module}/
│   ├── domain/
│   │   ├── entities/       # JPA entities with domain logic
│   │   ├── valueobjects/   # Value objects (Connector, OpeningHours)
│   │   └── enums/          # Domain enums
│   ├── application/
│   │   ├── services/       # Business logic orchestration
│   │   ├── dtos/           # Request/Response DTOs
│   │   └── exceptions/     # Module-specific exceptions
│   ├── infrastructure/
│   │   ├── repositories/   # Spring Data JPA repositories
│   │   ├── config/         # Module configuration
│   │   ├── security/       # Security components
│   │   └── external/       # External API integrations
│   └── presentation/
│       └── controllers/    # REST controllers
```

### Feature Modules

| Module | Description |
|--------|-------------|
| `auth` | Authentication (JWT), registration, password reset, OTP |
| `stations` | EV charging stations (OpenChargeMap + Google Places) |
| `cars` | Electric vehicle catalog |
| `profile` | User profile management |
| `reviews` | Station reviews and ratings |
| `infrastructure` | Cross-cutting concerns (email, SMS, Kafka, i18n) |

### Domain Entities with Business Logic

Entities contain domain validation and business rules:
```java
@Entity
public class User {
    // Domain validation: Business rules belong in domain layer
    public static void validatePasswordMatch(String password, String confirmation) {
        if (!password.equals(confirmation)) {
            throw new PasswordMismatchException();
        }
    }

    // Domain logic: Check if user can use charging stations
    public boolean canUseChargingStations() {
        return isEmailVerified();
    }
}
```

### Service Layer Pattern

Services orchestrate operations and call infrastructure:
```java
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EventPublisher eventPublisher;

    @Transactional
    public LoginResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        eventPublisher.publish("natomada.auth.events", UserLoggedInEvent.of(user.getId()));

        return LoginResponseDto.builder()
            .accessToken(accessToken)
            .user(UserResponseDto.fromEntity(user))
            .build();
    }
}
```

---

## Key Technologies

| Component | Technology |
|-----------|------------|
| Framework | Spring Boot 3.3.5 |
| Language | Java 21 |
| Database | PostgreSQL 16 |
| Cache | Redis 7 |
| Messaging | Apache Kafka (optional) |
| ORM | Spring Data JPA + Hibernate |
| Migrations | Flyway |
| Auth | JWT (jjwt 0.12.3) |
| API Docs | OpenAPI/Swagger (springdoc 2.6.0) |
| Build | Gradle 8 |
| Storage | AWS S3 |
| Email | Spring Mail (SMTP) |
| SMS | Integraflux API |

---

## API Endpoints

### Authentication (`/api/auth`)
- `POST /register` - Register new user
- `POST /login` - Login with email/password
- `POST /logout` - Logout (invalidate tokens)
- `POST /refresh` - Refresh access token
- `GET /me` - Get authenticated user
- `POST /forgot-password` - Request password reset
- `POST /reset-password` - Reset password with token
- `POST /otp/send` - Send OTP (email/SMS)
- `POST /otp/verify` - Verify OTP and login

### Stations (`/api/stations`)
- `GET /nearby` - Get nearby stations
- `GET /{id}` - Get station details
- `GET /favorites` - Get user favorites
- `POST /favorites` - Add favorite
- `DELETE /favorites/{id}` - Remove favorite
- `GET /search-history` - Get search history

### Vehicles (`/api/cars`)
- `GET /` - List all car models
- `GET /makes` - List makes (manufacturers)
- `GET /models` - List models by make

### Reviews (`/api/reviews`)
- `GET /station/{stationId}` - Get station reviews
- `POST /` - Create review
- `PUT /{id}` - Update review
- `DELETE /{id}` - Delete review

**API Documentation**: `http://localhost:8080/swagger-ui.html`

---

## Configuration

### Environment Variables (.env)

Key configuration from `.env.example`:
```bash
# Database
POSTGRES_DB=na_tomada
POSTGRES_USER=postgres
POSTGRES_PASSWORD=change-me

# Redis
REDIS_PASSWORD=change-me

# JWT
JWT_SECRET=your-super-secret-jwt-key-min-32-chars
JWT_EXPIRES_IN=3600000  # 1 hour in ms

# External APIs
OPENCM_API_KEY=your-opencm-api-key
GOOGLE_PLACES_API_KEY=your-google-places-api-key

# AWS S3
AWS_S3_BUCKET_NAME=natomada-images
AWS_REGION=us-east-1
AWS_ACCESS_KEY_ID=your-key
AWS_SECRET_ACCESS_KEY=your-secret

# Email (SMTP)
MAIL_HOST=smtp.hostinger.com
MAIL_PORT=587
MAIL_USERNAME=noreply@yourdomain.com
MAIL_PASSWORD=your-password

# SMS (Integraflux)
SMS_INTEGRAFLUX_TOKEN=your-token
SMS_INTEGRAFLUX_ENABLED=true
```

### Spring Profiles

- `docker` - Docker environment (uses service hostnames)
- `prod` - Production settings

---

## Code Conventions

### Lombok Annotations

Use Lombok for boilerplate reduction:
```java
@Data                    // Getters, setters, toString, equals, hashCode
@Builder                 // Builder pattern
@NoArgsConstructor       // Default constructor
@AllArgsConstructor      // All-args constructor
@RequiredArgsConstructor // Constructor for final fields (DI)
@Slf4j                   // SLF4J logger
```

### DTO Conventions

- Request DTOs: `{Action}RequestDto` (e.g., `LoginRequestDto`)
- Response DTOs: `{Entity}ResponseDto` (e.g., `UserResponseDto`)
- Use `@Builder` for construction
- Use `fromEntity()` static method for entity-to-DTO mapping

### Exception Handling

Custom exceptions extend appropriate base:
```java
// Domain exceptions in application/exceptions/
public class InvalidCredentialsException extends AuthException {
    public InvalidCredentialsException() {
        super("auth.invalid.credentials");  // i18n key
    }
}
```

Global exception handler in `GlobalExceptionHandler.java` maps exceptions to HTTP responses.

### Internationalization (i18n)

Messages in `src/main/resources/messages_{locale}.properties`:
- `messages_pt_BR.properties` - Portuguese (Brazil)
- `messages_en_US.properties` - English (US)

Use `MessageSourceService` to get localized messages:
```java
@RequiredArgsConstructor
public class MyService {
    private final MessageSourceService messageService;

    public void someMethod() {
        throw new RuntimeException(messageService.getMessage("station.not.found"));
    }
}
```

---

## External API Integration

### OpenChargeMap

Primary source for EV charging station data:
- Service: `OpenChargeMapService`
- Config: `opencm.api.key`, `opencm.api.base-url`

### Google Places API

Enrichment source for photos, ratings, amenities:
- Service: `GooglePlacesService`
- Uses Places API v1 for EV connector data
- Config: `google.places.api.key`

### Integraflux SMS

SMS delivery for OTP and notifications:
- Service: `IntegrafluxSmsService`
- Config: `sms.integraflux.*`

---

## Database

### Flyway Migrations

Migrations in `src/main/resources/db/migration/`:
- Use idempotent statements (`IF NOT EXISTS`, `IF EXISTS`)
- Always test migrations before deployment

### Entity Relationships

```
User (1) ─┬── (N) RefreshToken
          ├── (N) Favorite
          ├── (N) SearchHistory
          └── (N) Review

Station (1) ──── (N) Review
```

---

## Security

### JWT Authentication

- Access token: Short-lived (1 hour default)
- Refresh token: Long-lived (7 days), stored in database
- Filter: `JwtAuthenticationFilter`

### Public Endpoints

Configured in `SecurityConfig.java`:
- `/api/auth/**` - Authentication endpoints
- `/api/cars/**` - Vehicle catalog (public)
- `/v3/api-docs/**`, `/swagger-ui/**` - API documentation
- `/actuator/**` - Health checks

---

## Event System

### Kafka Events (Optional)

Events published via `EventPublisher`:
- `UserRegisteredEvent`
- `UserLoggedInEvent`
- `UserLoggedOutEvent`

Kafka can be disabled via `kafka.enabled=false`.

---

## Testing

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "AuthServiceTest"

# Run with test report
./gradlew test jacocoTestReport
```

---

## Commit Conventions

Follow Conventional Commits:
- `feat:` New feature
- `fix:` Bug fix
- `refactor:` Code refactoring
- `docs:` Documentation
- `test:` Tests
- `chore:` Maintenance

**Rules:**
- Short one-line messages (max 72 chars)
- No co-author tags
- Commit by small modules
- Only commit when explicitly requested

---

## Byterover MCP Tools

### `byterover-store-knowledge`
Use when learning patterns, encountering solutions, or completing significant tasks.

### `byterover-retrieve-knowledge`
Use before starting tasks, making architectural decisions, or debugging.
