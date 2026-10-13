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

## Technology Stack

- Java 17
- Spring Boot 3.3.x
- Spring Web MVC
- Spring Data JPA
- Hibernate ORM
- H2 in-memory database
- Maven
- JUnit 5 + Mockito

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
      service/
        CommunicationLogService.java
      repository/
        CommunicationLogRepository.java
      model/
        CommunicationLog.java
      dto/
        CommunicationLogRequest.java
        CommunicationLogResponse.java
      exception/
        ApiError.java
        GlobalExceptionHandler.java
    resources/
      application.properties
  test/
    java/com/example/auditlog/
      controller/
        CommunicationLogControllerTest.java
      service/
        CommunicationLogServiceTest.java
```

## Endpoints

### Create communication log

Endpoint:

```http
POST /api/communication-logs
Content-Type: application/json
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

Example response:

```json
{
  "id": 1,
  "auditId": "AUD-1001",
  "interactionType": "EMAIL",
  "notes": "Follow-up email sent to client regarding evidence request.",
  "interactionDate": "2026-10-12",
  "createdAt": "2026-10-12T09:15:30"
}
```

### Get logs by audit ID

Endpoint:

```http
GET /api/communication-logs?auditId=AUD-1001
```

Example response:

```json
[
  {
    "id": 1,
    "auditId": "AUD-1001",
    "interactionType": "EMAIL",
    "notes": "Follow-up email sent to client regarding evidence request.",
    "interactionDate": "2026-10-12",
    "createdAt": "2026-10-12T09:15:30"
  }
]
```

### Get log by ID

Endpoint:

```http
GET /api/communication-logs/{id}
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

Example error response:

```json
{
  "timestamp": "2026-10-12T09:20:10",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": [
    "interactionDate: Interaction date cannot be in the future"
  ]
}
```

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

## Why This Project Is Good for Interviews

This project is intentionally small but covers several important backend development concepts:

- REST API design
- Dependency injection
- Service-layer architecture
- Validation and business logic
- Hibernate/JPA persistence
- Data transfer objects
- Exception handling
- Automated testing

It is easy to explain, easy to demo, and easy to extend if the interviewer asks follow-up questions.

## Example curl Commands

### Create a log

```bash
curl -X POST http://localhost:8080/api/communication-logs \
  -H "Content-Type: application/json" \
  -d '{
    "auditId": "AUD-1001",
    "interactionType": "EMAIL",
    "notes": "Follow-up email sent to client regarding documentation request.",
    "interactionDate": "2026-10-12"
  }'
```

### Get logs by audit ID

```bash
curl "http://localhost:8080/api/communication-logs?auditId=AUD-1001"
```

### Get a single log by ID

```bash
curl http://localhost:8080/api/communication-logs/1
```

## Future Enhancements

This is a solid base for a production-grade application. Possible next improvements include:

- MySQL or PostgreSQL instead of H2
- User authentication and authorization
- Pagination and filtering
- Audit trail/history tracking
- Docker support
- Flyway/Liquibase database migrations
- Swagger/OpenAPI documentation

## Summary

This project demonstrates a clean, practical backend implementation for an audit communications log feature using Java, Spring Boot, Spring MVC, and Hibernate. It is small enough to explain quickly, but comprehensive enough to showcase real engineering principles.
