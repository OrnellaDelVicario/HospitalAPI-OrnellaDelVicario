# Hospital API Backend

This is a Spring Boot RESTful API for managing hospital staff and patient data.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Documentation & Testing](#documentation--testing)

## Features

- CRUD operations for Staff (Doctors, Nurses, Admin).
- CRUD operations for Patients.
- Custom queries for patient statistics:
    - Patients assigned to a specific doctor.
    - Patients with bill amount above average.
    - Average bill per department.
    - Doctors with more than a minimum count of patients.

## Technologies Used

- **Java 17+** (or your specific Java version, e.g., 21)
- **Spring Boot 3.x.x**
- **Spring Data JPA**
- **PostgreSQL** (Database)
- **Maven** (Build Tool)
- **Lombok** (Optional, for reducing boilerplate code if used)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK) 17+** installed.
- **Maven 3.x+** installed.
- **PostgreSQL** database server installed and running.
- An IDE like **IntelliJ IDEA** (recommended) or VS Code with Java extensions.

## Setup Instructions

Follow these steps to get the project up and running on your local machine:

### 1. Clone the Repository

```bash
git clone https://github.com/OrnellaDelVicario/HospitalAPI-OrnellaDelVicario.git
cd hospital-api-backend

