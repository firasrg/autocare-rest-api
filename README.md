# Car Service REST API sample

## Overview

The Car Data Service API is a Spring Boot application designed to expose comprehensive data about cars and related entities through a single endpoint. It uses a custom JSON builder to structure the data.

## Features

- **Expose Car Data**: Retrieve detailed information about cars, including related entities like clients, maintainers, and tools.
- **Custom JSON Structure**: Generate JSON responses with a custom JSON builder, maintaining the order of attributes.

## Technology Stack

- **Spring Boot**: Version 3.2
- **Java**: Version 17
- **JPA**: For database access
- **Jackson**: For JSON conversion
- **H2 Database**: For in-memory database support

## Getting Started

### Prerequisites

- Java 17
- Maven (for building the project)
- H2 database configuration (pre-configured for in-memory use)

### Installation

1. **Clone the Repository**

```bash
git clone https://github.com/yourusername/car-data-service-api.git
cd car-data-service-api
```

2. **Build the project**
```bash 
mvn clean install
```

3. **Run the Application**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

### Configuration
The `LoadDataConfig` class, which populates the database with sample data, is configured to run with the "demo" profile. Ensure to use this profile to load sample data.

### Usage
**Endpoint**: `/api/cars`

**Method**: `GET`

**Response Example:**
```json
[
    {
        "car": {
            "id": 1,
            "model": "Model S",
            "make": "Tesla",
            "clientName": "Client One",
            "maintainerName": "Technician One",
            "tools": [
                {
                    "id": 1,
                    "name": "Wrench"
                },
                {
                    "id": 2,
                    "name": "Screwdriver"
                }
            ]
        }
    }
]
```





