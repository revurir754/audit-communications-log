# Audit Communications Log

A small, interview-friendly Spring Boot backend for tracking communication events related to an audit. The application demonstrates a clean layered architecture using Spring MVC, Spring Data JPA, Hibernate, validation, and RESTful API design.

## Project Goal

A tax analyst or audit team member wants to record details of communications tied to an audit case. For example, they may log an email, phone call, meeting, or internal note associated with a specific audit ID. The system validates the request and stores the communication log.

## Features

- Create a new communication log
- Retrieve all logs for a given audit ID
- Retrieve a specific log by ID
- Spring MVC REST endpoints
- Spring Data JPA + Hibernate persistence
- DTO-based request/response design
- Input validation using Bean Validation
- Global exception handling
- H2 in-memory database for local running and demos
- Unit and controller tests
- JWT-based authentication (demo user)

## Technology Stack

- Java 17
- Spring Boot 3.3.x
- Spring Web MVC
- Spring Data JPA
- Hibernate ORM
- H2 in-memory database
- Maven
- JUnit 5 + Mockito
- Spring Security + JWT

## Application Flow

The project follows a standard layered Spring architecture:

1. Controller layer handles HTTP requests and returns responses.
2. Service layer contains business logic and validation rules.
3. Repository layer persists and retrieves data using Spring Data JPA.
4. Entity layer maps Java objects to database tables using Hibernate.
5. DTOs keep the API contract separate from database entities.
6. Exception handler centralizes error responses.

This makes the project easy to explain in interviews and easy to extend later.

## Main Project Structure

```text
src/
  main/
    java/com/example/auditlog/
      AuditCommunicationsLogApplication.java
      controller/
        CommunicationLogController.java
        AuthController.java
      service/
        CommunicationLogService.java
      repository/
        CommunicationLogRepository.java
      model/
        CommunicationLog.java
      dto/
        CommunicationLogRequest.java
        CommunicationLogResponse.java
        AuthRequest.java
        AuthResponse.java
      exception/
        ApiError.java
        GlobalExceptionHandler.java
      security/
        SecurityConfig.java
        JwtTokenUtil.java
        JwtAuthenticationFilter.java
        DemoUserDetailsService.java
    resources/
      application.properties
  test/
    java/com/example/auditlog/
      controller/
        CommunicationLogControllerTest.java
      service/
        CommunicationLogServiceTest.java
```

## Authentication (JWT)

This demo uses JWT for authentication. There is a demo user hard-coded in the application:

- username: `demo`
- password: `demo-password`

Login endpoint:

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "demo",
  "password": "demo-password"
}
```

Response:

```json
{ "token": "<JWT_TOKEN>" }
```

Use the token to access protected endpoints by adding the Authorization header:

```
Authorization: Bearer <JWT_TOKEN>
```

The login endpoint and h2 console are left public; all other endpoints require a valid token.

## Endpoints

### Create communication log

Endpoint:

```http
POST /api/communication-logs
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

Example request body:

```json
{
  "auditId": "AUD-1001",
  "interactionType": "EMAIL",
  "notes": "Follow-up email sent to client regarding evidence request.",
  "interactionDate": "2026-10-12"
}
```

### Get logs by audit ID

Endpoint:

```http
GET /api/communication-logs?auditId=AUD-1001
Authorization: Bearer <JWT_TOKEN>
```

### Get log by ID

Endpoint:

```http
GET /api/communication-logs/{id}
Authorization: Bearer <JWT_TOKEN>
```

## Validation Rules

The request is validated before saving:

- `auditId` is required and may contain only letters, numbers, and hyphens
- `interactionType` must be one of:
  - `EMAIL`
  - `PHONE`
  - `MEETING`
  - `NOTE`
  - `REPORT`
  - `OTHER`
- `notes` must be between 5 and 2000 characters
- `interactionDate` is required and cannot be in the future

Additional service-layer rules ensure the business logic is enforced even before persistence.

## Exception Handling

The application returns consistent JSON error payloads for:

- validation errors
- bad requests
- missing records
- unexpected server errors

## Run the Project

### Prerequisites

- Java 17+
- Maven

### Start the app

```bash
git clone https://github.com/revurir754/audit-communications-log.git
cd audit-communications-log
./mvnw spring-boot:run
```

If Maven wrapper is not available in your environment, use:

```bash
mvn spring-boot:run
```

The app runs on:

```text
http://localhost:8080
```

## H2 Database Console

The project uses an in-memory H2 database for demo purposes.

Open:

```text
http://localhost:8080/h2-console
```

Use the following connection details:

- JDBC URL: `jdbc:h2:mem:auditdb`
- Username: `sa`
- Password: empty

## Testing

Run the test suite:

```bash
./mvnw test
```

This includes:

- controller tests using MockMvc
- service tests using Mockito
- validation and business-rule checks

## Example curl Commands

### Login and get a token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"demo-password"}'
```

### Create a log (use token returned from login)

```bash
curl -X POST http://localhost:8080/api/communication-logs \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "auditId": "AUD-1001",
    "interactionType": "EMAIL",
    "notes": "Follow-up email sent to client regarding documentation request.",
    "interactionDate": "2026-10-12"
  }'
```

### Get logs by audit ID

```bash
curl "http://localhost:8080/api/communication-logs?auditId=AUD-1001" -H "Authorization: Bearer <JWT_TOKEN>"
```

## Future Enhancements

- Refresh tokens and token revocation
- User persistence instead of hard-coded demo user
- Role-based authorization (admin vs user)
- DB migrations (Flyway/Liquibase)
- Docker + Kubernetes deployment

## Summary

This project demonstrates a small, production-oriented backend with JWT-based authentication to secure the API. It's easy to run locally and extend for production scenarios.
