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
- ✅ Transaction entity structure
- ✅ Category management for organizing finances
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

## 🗺 Roadmap

### Phase 1: Core Financial Features

- [ ] Complete CRUD operations for transactions
- [ ] Income and expense tracking
- [ ] Category management API
- [ ] Transaction filtering and search
- [ ] Monthly/yearly financial summaries

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
