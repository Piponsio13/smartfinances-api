# 💰 Smart Finances API

A modern RESTful API for personal finance management built with Spring Boot, providing secure user authentication and financial transaction tracking.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED.svg)](https://www.docker.com/)

## 📋 Table of Contents

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

## 🎯 Overview

Smart Finances API is a backend service designed to help users manage their personal finances efficiently. It provides a secure foundation for tracking income, expenses, and categorizing transactions with user authentication powered by JWT tokens.

## ✨ Current Features

### Authentication & Security

- ✅ User registration and login system
- ✅ JWT-based authentication
- ✅ Secure password handling with Spring Security
- ✅ Role-based access control (RBAC)

### Core Functionality

- ✅ User management system
- ✅ Complete transaction CRUD operations
- ✅ Category management (default + custom categories)
- ✅ Income and expense tracking
- ✅ Secure transaction-to-user association
- ✅ Health check endpoint for monitoring

### Infrastructure

- ✅ PostgreSQL database integration
- ✅ Docker containerization
- ✅ Docker Compose for easy deployment
- ✅ Spring Actuator for application monitoring

## 🛠 Tech Stack

### Backend

- **Java 21** - Latest LTS version of Java
- **Spring Boot 4.0.1** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database access layer
- **Hibernate** - ORM framework

### Database

- **PostgreSQL 15** - Relational database

### Security

- **JWT (JSON Web Tokens)** - Token-based authentication
- **JJWT 0.12.5** - JWT implementation

### DevOps

- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Maven** - Build automation

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.8+
- Docker & Docker Compose (for containerized deployment)
- PostgreSQL 15 (if running locally without Docker)

### Environment Variables

Create a `.env` file in the root directory with the following variables:

```env
# Database Configuration
POSTGRES_DB=smartfinances
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password

# Spring Datasource Configuration
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/smartfinances
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password

# JWT Configuration
JWT_SECRET=your_super_secret_jwt_key_here_minimum_256_bits
```

### Running with Docker

The easiest way to run the application is using Docker Compose:

```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down
```

The API will be available at `http://localhost:8080`

### Running Locally

1. **Start PostgreSQL** (if not using Docker)

2. **Build the project**

```bash
./mvnw clean install
```

3. **Run the application**

```bash
./mvnw spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/smartfinances-api-0.0.1-SNAPSHOT.jar
```

## 📡 API Endpoints

### Health & Monitoring

```http
GET /health
```

Check if the API is running and healthy.

### Authentication

#### Register

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "string",
  "password": "string",
  "email": "string"
}
```

#### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "string",
  "password": "string"
}
```

Returns a JWT token for authenticated requests.

### Protected Endpoints

All subsequent requests require the JWT token in the Authorization header:

```http
Authorization: Bearer <your_jwt_token>
```

### Categories

#### Get All User Categories

```http
GET /api/category/all
Authorization: Bearer <your_jwt_token>
```

Retrieves all categories (default + custom) for the authenticated user.

**Response:**

```json
{
  "data": [
    {
      "name": "Salary",
      "type": "INCOME"
    },
    {
      "name": "Food & Dining",
      "type": "EXPENSE"
    }
  ],
  "message": "All user categories retrieved successfully",
  "statusCode": 200
}
```

#### Create Custom Category

```http
POST /api/category/create
Authorization: Bearer <your_jwt_token>
Content-Type: application/json

{
  "categoryName": "string",
  "type": "INCOME" | "EXPENSE"
}
```

Creates a new custom category for the authenticated user.

**Response:**

```json
{
  "data": "Category Name",
  "message": "Category created successfully",
  "statusCode": 201
}
```

#### Delete Category

```http
DELETE /api/category/delete/{name}
Authorization: Bearer <your_jwt_token>
```

Deletes a category by name for the authenticated user.

**Response:**

```json
{
  "data": null,
  "message": "Category deleted successfully",
  "statusCode": 200
}
```

**Default Categories:**

When a user registers, the following categories are automatically created:

**Income Categories:**

- Salary
- Freelance
- Investments
- Other Income

**Expense Categories:**

- Food & Dining
- Transportation
- Utilities
- Housing
- Healthcare
- Shopping
- Education
- Subscriptions
- Personal Care
- Other Expenses

### Transactions

#### Create Transaction

```http
POST /api/transactions
Authorization: Bearer <your_jwt_token>
Content-Type: application/json

{
  "amount": 150.00,
  "description": "Grocery shopping",
  "type": "EXPENSE",
  "categoryId": 1,
  "date": "2026-01-20T10:30:00"
}
```

Creates a new transaction for the authenticated user.

**Response:**

```json
{
  "data": null,
  "message": "Transaction created successfully",
  "statusCode": 201
}
```

#### Get All User Transactions

```http
GET /api/transactions
Authorization: Bearer <your_jwt_token>
```

Retrieves all transactions for the authenticated user.

**Response:**

```json
{
  "data": [
    {
      "id": 1,
      "amount": 150.0,
      "description": "Grocery shopping",
      "type": "EXPENSE",
      "categoryName": "Food & Dining",
      "date": "2026-01-20T10:30:00"
    }
  ],
  "message": "All user transactions retrieved successfully",
  "statusCode": 200
}
```

#### Get Transaction by ID

```http
GET /api/transactions/{id}
Authorization: Bearer <your_jwt_token>
```

Retrieves a specific transaction by ID (must belong to authenticated user).

**Response:**

```json
{
  "data": {
    "id": 1,
    "amount": 150.0,
    "description": "Grocery shopping",
    "type": "EXPENSE",
    "categoryName": "Food & Dining",
    "date": "2026-01-20T10:30:00"
  },
  "message": "Transaction retrieved successfully",
  "statusCode": 200
}
```

#### Update Transaction

```http
PUT /api/transactions/{id}
Authorization: Bearer <your_jwt_token>
Content-Type: application/json

{
  "amount": 175.50,
  "description": "Updated grocery shopping",
  "type": "EXPENSE",
  "categoryId": 1,
  "date": "2026-01-20T10:30:00"
}
```

Updates an existing transaction (must belong to authenticated user).

**Response:**

```json
{
  "data": null,
  "message": "Transaction updated successfully",
  "statusCode": 200
}
```

#### Delete Transaction

```http
DELETE /api/transactions/{id}
Authorization: Bearer <your_jwt_token>
```

Deletes a transaction (must belong to authenticated user).

**Response:**

```json
{
  "data": null,
  "message": "Transaction deleted successfully",
  "statusCode": 200
}
```

#### Get Transaction Summary (with optional month/year)

```http
GET /api/transactions/summary?month=1&year=2026
Authorization: Bearer <your_jwt_token>
```

Returns a summary of income, expenses, and balance for the specified month and year. If no month/year is provided, returns summary for all transactions.

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

## 🗺 Roadmap

### Phase 1: Core Financial Features 🚧

- [x] Complete CRUD operations for transactions
- [x] Income and expense tracking
- [x] Category management API
- [x] Transaction filtering and search (with JPA Specifications)
- [ ] Monthly/yearly financial summaries (In Progress)

### Phase 2: Analytics & Reporting

- [ ] Dashboard statistics
- [ ] Spending patterns analysis
- [ ] Budget tracking and alerts
- [ ] Export transactions (CSV, PDF)
- [ ] Visual reports and charts data

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

### Infrastructure Improvements

- [ ] API documentation with Swagger/OpenAPI
- [ ] Unit and integration tests
- [ ] CI/CD pipeline
- [ ] Logging and monitoring improvements
- [ ] Rate limiting and API throttling
- [ ] Database migration management (Flyway/Liquibase)
- [ ] AWS deployment with ECS (Elastic Container Service)
- [ ] AWS RDS PostgreSQL for production database

## 📂 Project Structure

```
smartfinances-api/
├── src/
│   ├── main/
│   │   ├── java/.../smartfinances_api/
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── request/         # Request DTOs
│   │   │   │   └── response/        # Response DTOs
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── enums/               # Enumerations
│   │   │   ├── repository/          # Data repositories
│   │   │   ├── security/            # Security configuration
│   │   │   ├── service/             # Business logic
│   │   │   └── utils/               # Utility classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Test files
├── docker-compose.yml               # Docker Compose configuration
├── Dockerfile                       # Docker image definition
└── pom.xml                          # Maven configuration
```

## 👤 Author

**Felipe Lara**

- GitHub: [@piponsio](https://github.com/piponsio)
- LinkedIn: [Luis Felipe Lara Adame](https://www.linkedin.com/in/luis-felipe-lara-adame-b2a971292/)
