# CarSell Platform

A modern, real-world car selling platform built with Java 21, Spring Boot, Gradle, and JWT-based OAuth2 authentication. This project demonstrates a complete end-to-end solution including secure user management, JWT authentication with CSRF protection, RESTful endpoints for managing users and cars, and integration tests.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Configuration](#configuration)
- [Database Migrations](#database-migrations)
- [Security](#security)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Endpoints](#endpoints)
- [Usage in Postman](#usage-in-postman)
- [License](#license)

---

## Overview

CarSell Platform is a robust car selling application that allows users to register, authenticate, and list cars for sale. It leverages JWT for authentication and uses modern Java 21 APIs for time and concurrency. The platform includes a comprehensive security configuration that supports stateful sessions with CSRF protection and uses a dedicated user authentication mechanism.

---

## Features

- **User Management:**
   - Create, update, retrieve, and delete users.
   - Secure password storage using BCrypt hashing.
   - JWT-based authentication for login.

- **Car Management:**
   - Create, update, retrieve, and delete car listings.
   - Supports polymorphic car types (e.g., Sedan, SUV, Truck) using single table inheritance.
   - Domain logic for calculating derived fields (age, discount amount, etc.) via transient getters.

- **Security:**
   - OAuth2 style authentication using JWT tokens.
   - CSRF protection enabled with a dedicated CSRF endpoint.
   - Custom exception handling for robust error responses.

- **Testing:**
   - Integration tests using MockMvc and TestRestTemplate.
   - In-memory H2 database configured for testing with Flyway migrations.

---

## Architecture

The application is built using a layered architecture:

- **Controller Layer:**  
  Exposes RESTful endpoints (e.g., `/api/users`, `/api/cars`) and handles HTTP requests.

- **Service Layer:**  
  Contains business logic for users and cars, including secure password encoding and domain validations.

- **Repository Layer:**  
  Uses Spring Data JPA to interact with the database.

- **Security Layer:**  
  Implements JWT-based authentication, a custom `UserDetailsService`, and stateful session management with CSRF protection.

- **Mapping Layer:**  
  Maps between domain entities (e.g., `Car`, `User`) and DTOs (e.g., `CreateUserRequest`, `SedanCarRequest`, `CarResponse`).

---

## Technologies Used

- **Java 21**
- **Spring Boot 3.x**
- **Spring Security 6.1+**
- **JWT (JJWT 0.12.0+)**
- **Gradle**
- **PostgreSQL (production) & H2 (for testing)**
- **Flyway for database migrations**
- **JUnit 5 & MockMvc for integration testing**

---

## Configuration

Configuration properties are externalized. For example, JWT properties are set in `application.yml` or `application-test.yml`:

```yaml
# application.yml (or application-test.yml for tests)
jwt:
  secret: "U29tZUJhc2U2NEVuY29kZWRTZWNyZXRLZXlGb3JIUzUxMjRDb21wYW55MTIzNDU2Nzg5MA=="
  expirationMs: 86400000

```
## Database Migrations

Flyway scripts manage database schema changes. 


## Security

The security layer includes:

*    JWTUtil: A utility class using JJWT 0.12.0+ and Java 21's time API to generate, validate, and parse JWT tokens.
     
*    JWTAuthenticationFilter: Intercepts /login requests, authenticates using username/password, generates a JWT, and stores it in the session.

*    JWTAuthorizationFilter: Checks incoming requests for a valid JWT (in header or session) and sets authentication in the SecurityContext.

*    CustomUserDetailsService: Loads user details from the database.

*    SecurityConfig: Configures Spring Security, enabling stateful sessions with CSRF protection.


## Running the Application

* Build the Project:
Run ./gradlew build from the project root.

* Run the Application:
Use ./gradlew bootRun to start the application.

* Database:
Ensure your PostgreSQL database is running and configured. For testing, the application uses H2 with settings from application-test.yml.

## Testing

* Integration Tests:
The project includes integration tests using JUnit 5, MockMvc, and TestRestTemplate.

* CSRF:
In tests, you can either obtain the CSRF token via the /csrf endpoint or disable CSRF for testing purposes in your test configuration.

* Run Tests:
Use ./gradlew test to run all tests.


## Endpoints

*    User Management:

        POST /api/users – Create a new user.

        GET /api/users/{id} – Retrieve a user.

        PUT /api/users/{id} – Update a user.

        DELETE /api/users/{id} – Delete a user.

*    Authentication:

        POST /login – Authenticate and receive a JWT token.

*    Car Management:

        GET /api/cars – Retrieve all cars.

        GET /api/cars/{id} – Retrieve a car by id.

        POST /api/cars – Create a new car.

        PUT /api/cars/{id} – Update a car.

        DELETE /api/cars/{id} – Delete a car.

*    CSRF Token:

        GET /csrf – Retrieve CSRF token (if needed by client).
## Usage in Postman

Usage in Postman
Create New User

    Endpoint: POST http://localhost:8080/api/users

    Headers: Content-Type: application/json

    Body:

    {
      "username": "seller1",
      "login": "seller1",
      "email": "seller@example.com",
      "name": "Car",
      "firstName": "Car",
      "lastName": "Seller",
      "password": "securePass123",
      "roles": ["SELLER"]
    }

Authenticate User

    Endpoint: POST http://localhost:8080/login

    Headers: Content-Type: application/json

    Body:

    {
      "username": "seller1",
      "password": "securePass123"
    }

    Response:
    Look for the JWT token in the Authorization header (or in the JSON body).

Create a Car

    Endpoint: POST http://localhost:8080/api/cars

    Headers:

        Content-Type: application/json

        Authorization: Bearer <JWT_TOKEN>

    Body:

{
"@type": "SEDAN",
"make": "Toyota",
"model": "Camry",
"year": 2020,
"price": 25000,
"sellerId": 1,
"description": "A reliable sedan.",
"trunkCapacity": 15.5
}

Note: The @type property is used for polymorphic deserialization.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

**Dzmitry Ivaniuta** — [diafter@gmail.com](mailto:diafter@gmail.com) — [GitHub](https://github.com/DimitryIvaniuta)
