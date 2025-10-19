# Space Service

## Overview

The **Space Service** is responsible for managing physical spaces and related entities within the CUE Event Management System. It provides functionality for registering, updating, and querying spaces such as classrooms, auditoriums, and laboratories, as well as managing their types, statuses, resources, and associated campuses.

---

## Purpose

This service centralizes all operations related to physical space management in the university. It ensures that the allocation, reservation, and availability of spaces are handled efficiently and consistently across events. It exposes endpoints to manage:

* **Spaces** (rooms, auditoriums, laboratories, etc.)
* **Space Types** (e.g., classroom, lab, meeting room)
* **Space Statuses** (available, reserved, under maintenance)
* **Space Resources** (e.g., projectors, sound systems, internet)
* **Campuses** (university locations with their facilities)

It serves as a foundational component for event scheduling and logistics, ensuring that space allocation avoids conflicts and maximizes utilization.

---

## Versions

| Component                                   | Version |
| ------------------------------------------- | ------- |
| **Java**                                    | 17      |
| **Spring Boot**                             | 3.5.4   |
| **Gradle**                                  | 8.14.3  |
| **Bancolombia Clean Architecture Scaffold** | 3.26.1  |

---

## Architecture

The Space Service is implemented following the **Bancolombia Clean Architecture Scaffold**, ensuring separation of concerns, maintainability, and scalability.

```
space-service/
├── applications/             # Application entry points and configurations
├── domain/                   # Core entities, value objects, and use cases
├── infrastructure/            # Adapters, repositories, mappers, and controllers
├── build.gradle               # Gradle configuration
└── settings.gradle            # Project settings
```

### Layers

* **Domain:** Defines core entities such as `Space`, `SpaceType`, `SpaceStatus`, `SpaceResource`, and `Campus`.
* **Use Cases:** Contains logic for creating, updating, and managing spaces and related entities.
* **Infrastructure:** Implements persistence, mapping, and external communication.
* **Entry Points:** Exposes REST APIs for external consumers like Event Service or Admin Panel.

---

## Environment Variables

The following environment variables are used by the Space Service:

```bash
# -----------------------------------
# Server Configuration
# -----------------------------------
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev

# -----------------------------------
# Database Configuration
# -----------------------------------
DB_URL=jdbc:mysql://mysql-space:3306/space_service?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
DB_USERNAME=space_user
DB_PASSWORD=space_password

# -----------------------------------
# Internal Communication
# -----------------------------------
INTERNAL_SECRET=your-internal-service-secret
EUREKA_URL=http://discovery-service:8761/eureka/

# -----------------------------------
# Logging Configuration
# -----------------------------------
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_CO.EDU.CUE=DEBUG

# -----------------------------------
# CORS Configuration
# -----------------------------------
CORS_ALLOWED_ORIGINS=http://localhost:4200,http://localhost:3000
```

---

## Key Endpoints

| Method | Endpoint               | Description                             |
| ------ | ---------------------- | --------------------------------------- |
| GET    | `/api/spaces`          | Retrieve all registered spaces          |
| POST   | `/api/spaces`          | Register a new space                    |
| PUT    | `/api/spaces/{id}`     | Update an existing space                |
| DELETE | `/api/spaces/{id}`     | Delete a space                          |
| GET    | `/api/space-types`     | Retrieve all space types                |
| POST   | `/api/space-types`     | Create a new space type                 |
| GET    | `/api/space-statuses`  | Retrieve available space statuses       |
| POST   | `/api/space-statuses`  | Create a new space status               |
| GET    | `/api/space-resources` | Retrieve all resources                  |
| POST   | `/api/space-resources` | Create a new resource                   |
| GET    | `/api/campuses`        | Retrieve campuses with location details |
| POST   | `/api/campuses`        | Register a new campus                   |

---

## Security

* Uses **internal service authentication** via `INTERNAL_SECRET` for inter-service requests.
* Integrated with **Auth Service** for user authentication and role validation.
* **RBAC (Role-Based Access Control)** ensures only administrators can manage spaces and configurations.

---

## Related Services

* **Event Service:** Uses the Space Service to check availability and reserve rooms for events.
* **File Service:** Manages multimedia files or images of spaces and campuses.
