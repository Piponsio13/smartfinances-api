# Smart Finances API

A RESTful API for personal finance management built with Spring Boot, providing secure user authentication, transaction tracking, category management, budget alerts, and financial dashboards.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED.svg)](https://www.docker.com/)

## Table of Contents

- [Overview](#overview)
- [Current Features](#current-features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Environment Variables](#environment-variables)
  - [Running with Docker](#running-with-docker)
  - [Running Locally](#running-locally)
- [API Endpoints](#api-endpoints)
- [Roadmap](#roadmap)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Overview

Smart Finances API is a backend service designed to help users manage their personal finances efficiently. It provides a secure foundation for tracking income and expenses, managing categories and budgets, exporting transaction data, and visualizing monthly spending breakdowns — all protected by JWT authentication.

## Current Features

### Authentication & Security

- User registration and login
- JWT-based stateless authentication
- Secure password hashing with BCrypt
- Role-based access control (RBAC)
- Input validation on all endpoints (400 with field-level error messages)

### Core Functionality

- Complete transaction CRUD with advanced filtering (type, category, date range, amount range, description search)
- Category management (default + custom categories)
- Income and expense tracking
- Monthly/yearly financial summaries
- Budget tracking per category with real-time spent/remaining/exceeded status
- Dashboard with current-month summary and per-category spending breakdown
- Transaction export to CSV (with filter support)

### Infrastructure

- PostgreSQL database integration
- Docker containerization and Docker Compose
- Spring Actuator for health monitoring
- Swagger UI — interactive API documentation at `/swagger-ui.html`
- Profile-based configuration (`dev` profile for local development)

## Tech Stack

### Backend

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Security** — authentication and authorization
- **Spring Data JPA / Hibernate** — database access
- **Spring Validation** — request validation

### Database

- **PostgreSQL 15**

### Security

- **JWT / JJWT 0.12.5**

### DevOps

- **Docker / Docker Compose**
- **Maven**

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- Docker & Docker Compose (for containerized deployment)
- PostgreSQL 15 (if running locally without Docker)

### Environment Variables

Create a `.env` file in the root directory:

```env
# Database
POSTGRES_DB=smartfinances
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password

# Spring Datasource
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/smartfinances
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_super_secret_jwt_key_here_minimum_256_bits
```

### Running with Docker

```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down
```

The API will be available at `http://localhost:8080`.
Swagger UI: `http://localhost:8080/swagger-ui.html`

### Running Locally

1. Start PostgreSQL (if not using Docker)

2. Build and run with the `dev` profile (enables `ddl-auto=update` and SQL logging):

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Or with the JAR:

```bash
java -jar target/smartfinances-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

> Without the `dev` profile the app runs with `ddl-auto=validate`, which requires the schema to already exist.

## API Endpoints

All protected endpoints require:
```http
Authorization: Bearer <your_jwt_token>
```

---

### Authentication

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "string",
  "email": "string",       // must be a valid email
  "password": "string"     // minimum 8 characters
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "string",
  "password": "string"
}
```

**Response:**
```json
{
  "data": { "token": "<jwt>" },
  "message": "Successful Login",
  "statusCode": 200
}
```

---

### Categories

Default categories are created automatically on registration.

#### Get All Categories
```http
GET /api/category/all
```

**Response:**
```json
{
  "data": [
    { "id": 1, "name": "Salary", "type": "INCOME" },
    { "id": 5, "name": "Food & Dining", "type": "EXPENSE" }
  ],
  "message": "All user categories retrieved successfully",
  "statusCode": 200
}
```

#### Create Custom Category
```http
POST /api/category
Content-Type: application/json

{
  "categoryName": "string",
  "type": "INCOME" | "EXPENSE"
}
```

#### Delete Category
```http
DELETE /api/category/{id}
```

> Returns 409 Conflict if the category has existing transactions.

---

### Transactions

#### Create Transaction
```http
POST /api/transactions
Content-Type: application/json

{
  "amount": 150.00,          // must be > 0
  "description": "string",
  "type": "INCOME" | "EXPENSE",
  "categoryId": 1,
  "date": "2026-01-20T10:30:00"
}
```

#### Get All Transactions (with optional filters)
```http
GET /api/transactions?type=EXPENSE&categoryId=5&dateFrom=2026-01-01T00:00:00&dateTo=2026-01-31T23:59:59&minAmount=10&maxAmount=500&description=grocery
```

All filter parameters are optional.

#### Get Transaction by ID
```http
GET /api/transactions/{id}
```

#### Update Transaction
```http
PUT /api/transactions/{id}
Content-Type: application/json

{
  "amount": 175.50,
  "description": "string",
  "type": "EXPENSE",
  "categoryId": 1,
  "date": "2026-01-20T10:30:00"
}
```

#### Delete Transaction
```http
DELETE /api/transactions/{id}
```

#### Get Financial Summary
```http
GET /api/transactions/summary?month=1&year=2026
```

`month` and `year` are optional. When omitted, returns an all-time summary.

**Response:**
```json
{
  "data": {
    "totalIncome": 2000.0,
    "totalExpenses": 1500.0,
    "balance": 500.0,
    "transactionCount": 12
  },
  "message": "Transaction summary retrieved successfully",
  "statusCode": 200
}
```

#### Export Transactions to CSV
```http
GET /api/transactions/export?type=EXPENSE&categoryId=5
```

Accepts the same filter parameters as the list endpoint. Returns a `transactions.csv` file download.

---

### Dashboard

```http
GET /api/dashboard
```

Returns the current month's summary and a per-category spending breakdown sorted by highest spend.

**Response:**
```json
{
  "data": {
    "month": 7,
    "year": 2026,
    "totalIncome": 2000.0,
    "totalExpenses": 1200.0,
    "balance": 800.0,
    "transactionCount": 15,
    "spendingByCategory": [
      { "categoryName": "Food & Dining", "total": 400.0, "percentage": 33.33 },
      { "categoryName": "Transportation", "total": 200.0, "percentage": 16.67 }
    ]
  },
  "message": "Dashboard retrieved successfully",
  "statusCode": 200
}
```

---

### Budgets

#### Create Budget
```http
POST /api/budgets
Content-Type: application/json

{
  "categoryId": 5,
  "monthlyLimit": 400.00,
  "month": 7,
  "year": 2026
}
```

> Returns 409 Conflict if a budget already exists for that category and period.

#### Get All Budgets
```http
GET /api/budgets
```

Returns all budgets with live spending status.

**Response:**
```json
{
  "data": [
    {
      "id": 1,
      "categoryName": "Food & Dining",
      "monthlyLimit": 400.00,
      "actualSpending": 320.00,
      "remaining": 80.00,
      "exceeded": false,
      "month": 7,
      "year": 2026
    }
  ],
  "message": "Budgets retrieved successfully",
  "statusCode": 200
}
```

#### Delete Budget
```http
DELETE /api/budgets/{id}
```

---

## Error Responses

All errors follow the same envelope format:

| Status | Meaning |
|--------|---------|
| 400 | Validation failed — `data` contains a field→message map |
| 401 | Invalid credentials or missing/expired token |
| 404 | Resource not found |
| 409 | Conflict (duplicate email, duplicate budget, category has transactions) |
| 500 | Unexpected server error |

---

## Roadmap

### Phase 1: Core Financial Features

- [x] Complete CRUD operations for transactions
- [x] Income and expense tracking
- [x] Category management API
- [x] Transaction filtering and search
- [x] Monthly/yearly financial summaries

### Phase 2: Analytics & Reporting

- [x] Dashboard statistics with spending breakdown
- [x] Budget tracking and alerts
- [x] Export transactions to CSV
- [ ] Spending patterns analysis
- [ ] Visual reports and charts data
- [ ] PDF export

### Phase 3: Advanced Features

- [ ] Recurring transactions
- [ ] Multi-currency support
- [ ] Bill reminders and notifications
- [ ] Savings goals tracking
- [ ] Financial forecasting

### Phase 4: Integration & Enhancement

- [ ] Bank account integration (Plaid/Yodlee)
- [ ] Email notifications
- [ ] Mobile app support (separate project)
- [ ] Data import from other finance apps
- [ ] AI-powered insights and recommendations

### Infrastructure

- [x] API documentation with Swagger/OpenAPI
- [ ] Unit and integration tests
- [ ] CI/CD pipeline
- [ ] Database migration management (Flyway/Liquibase)
- [ ] Rate limiting and API throttling
- [ ] AWS deployment with ECS
- [ ] AWS RDS PostgreSQL for production database

## Project Structure

```
smartfinances-api/
├── src/
│   ├── main/
│   │   ├── java/.../smartfinances_api/
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── dto/
│   │   │   │   ├── request/         # Request DTOs (validated)
│   │   │   │   └── response/        # Response DTOs
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── enums/               # TransactionType, RoleType
│   │   │   ├── exception/           # GlobalExceptionHandler + custom exceptions
│   │   │   ├── repository/          # Spring Data repositories
│   │   │   ├── security/            # JWT filter and config
│   │   │   ├── service/
│   │   │   │   ├── auth/            # Login and registration
│   │   │   │   ├── budget/          # Budget logic
│   │   │   │   ├── category/        # Category logic
│   │   │   │   ├── dashboard/       # Dashboard aggregation
│   │   │   │   └── transaction/     # Transaction logic
│   │   │   └── utils/               # AuthUser, CustomResponse
│   │   └── resources/
│   │       ├── application.properties        # Base config (production-safe)
│   │       └── application-dev.properties    # Dev overrides (SQL logging, ddl=update)
│   └── test/
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## Author

**Felipe Lara**

- GitHub: [@piponsio](https://github.com/piponsio)
- LinkedIn: [Luis Felipe Lara Adame](https://www.linkedin.com/in/luis-felipe-lara-adame-b2a971292/)
