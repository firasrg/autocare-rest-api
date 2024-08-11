# Car Service System Overview

NOTE⚠️: _Reading this document may lead to a different perception of the project compared to what is written in the [README](../../README.md) file, so that it will appear to readers as a real software product of high quality, directed to real customers and available for sale/purchase. From this point, the developers and testers are expected to interact with the system as real users (non developers). We would like this to be the goal_ 😁 _, because technologies employed in this project, are mostly used for **enterprise** purpose, and we want to help developers to learn how to deal with such projects in an entertaining manner. It's worth noting that this project remains dedicated for learning objectives. In case you think about using this project for commercial uses, we remind you to read its [licence](../../LICENSE) carefully before you start._

NOTE⚠️: _The current code-base of the project was made quickly without thinking too much about long term objectives. With this document, it would be obvious to review the code-base and refactor it based on the information provided here. Thus, a new issue should be created for this [heavy] work._

## Primary Business-Objective (BO)

This section is about setting the _Primary Business Objective (BO)_ of the **Car Service** system, once it's ascertained, in terms of the “what, why, and how” of it, that objective is systematically broken down into small pieces based on prioritization.

### What is it (What?)

Comprehensive [Web application](https://en.wikipedia.org/wiki/Web_application) designed to streamline and manage car services efficiently. It provides an organized and client-friendly interface for managing car-related services, including repairs, maintenance, and customer requests and more.

### Reason (Why?)

To enhance customer satisfaction and operational efficiency, by providing a centralized system that automates and simplifies the management of car services. This leads to improved service delivery, reduced errors, and optimized resource utilization.

### Implementation (How?)

The system achieves its objectives by leveraging modern web technologies and best practices in software development. The system is developed using [Spring Boot](https://spring.io/projects/spring-boot), a robust [Java](https://www.java.com/en/download/help/whatis_java.html)-based framework, and is designed with scalability, flexibility, and user experience in mind.

The development process is based on the [12-factor](https://12factor.net/) methodology, ensuring it is a modern cloud-native application.

The [REST](https://restfulapi.net/) API setup will follow the [**MVC pattern**](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) to maintain clear separation between the model, view, and controller layers. [**Domain-Driven Design (DDD)**](https://en.wikipedia.org/wiki/Domain-driven_design) will be used to distinguish competitive features from normal ones, ensuring the system focuses on delivering value where it matters most.

To further clarify the system’s design, **[UML](https://en.wikipedia.org/wiki/Unified_Modeling_Language) diagrams** illustrate:
- Actors, scenarios, and relationships;
- Preconditions, post-conditions and error cases;
- Abstractions and enums;
- Database tables and their relationships;

## System Components

This section is about splitting the project's _Primary Business Objective_ into pieces, also known as components.

### Car Management

This component is focused on Car-related entities. It's composed of the following subcomponents.

1. **Cars**: Manage cars based on some characteristics like size, brand, type, etc...
2. **Issues**: Handle car issues like fixes, washes, parts replacement, exchanges, etc...
3. **Accessories**: Tracks and manage accessories available for cars.

### Customer Management
This component handles customer entities and their interactions with the system. It includes the following subcomponents:

### Staff Management
### Garage Management
### Car-Service Scheduling
### Financial Management

