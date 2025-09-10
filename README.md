# Authentication microservice

A robust authentication and authorization microservice built with **Spring Boot**, featuring email/password authentication, Google OAuth2 login, JWT access tokens, refresh tokens, email verification and password recovery flows. Fully unit-tested and documented with JavaDoc. Designed with a clean architecture approach.

---

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Endpoints](#endpoints)
- [Testing](#testing)
- [OpenAPI Documentation](#openapi-documentation)
- [Contributing](#contributing)

---

## Features

- User registration with email verification.
- Login with email/password or Google OAuth2.
- JWT-based access tokens and refresh tokens.
- Password hashing and secure validation.
- Redis-backed verification and recovery codes with TTL.
- Fully unit-tested use cases.
- Clean architecture: domain, application, and infrastructure layers separated.
- Hexagonal architecture: uncoupled implementations.
- Extensive JavaDoc documentation.
- OpenAPI documentation.
- Ready to integrate with ELK stack.
- Follows OWASP security best practices.

---

## Architecture

Design follows domain driven design (DDD) principles and Hexagonal Architecture criteria. 

---

## Getting Started

### Prerequisites

- Java 22
- Maven 
- Redis (for verification codes, refresh tokens and )
- MySQL or any relational DB for user persistence
- Google OAuth2 credentials (optional)

### Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

---

## Endpoints

| Method | Endpoint                      | Description                                         |
|--------|-------------------------------|-----------------------------------------------------|
| POST   | `/auth/register`              | Register a new user, sends verification code via email. |
| GET    | `/auth/register/verify`       | Verify registration code and activate account.      |
| POST   | `/auth/login`                 | Login with email/password.                          |
| POST   | `/auth/refresh`               | Refresh access token using a refresh token.         |
| GET    | `/auth/me`                    | Get current authenticated user info.                |
| PUT    | `/auth/me/password`           | Change user password.                               |
| PUT    | `/auth/me/username`           | Change username.                                    |
| PUT    | `/auth/me/email`              | Change email.                                       |
| DELETE | `/auth/me`                    | Delete user account.                                |
| GET    | `/auth/oauth/google`          | Redirect to Google OAuth login.                     |
| GET    | `/auth/oauth/google/callback` | Google OAuth callback handler.                      |
| POST   | `/auth/recover-password`      | Recover password (begin flow).                      | 
| POST   | `/auth/reset-password`        | Recover password (end flow).                        |

---

## Testing

* Unit tests cover use cases (RegisterUserUseCase, LoginUserUseCase, etc.).
* Mockito is used for mocking dependencies.
* Redis and DB interactions are abstracted via repositories for easy testing.

```bash
mvn test
```

---

## OpenAPI documentation

* Generated automatically via springdoc-openapi.
* Access UI:

```url
http://localhost:8080/swagger-ui/index.html
```

---

## Contributing

Feel free to contact me if you want to contribute. PR's are open.