# Transactional Outbox Pattern Demonstration

## Overview
The Transactional Outbox Pattern is an approach used in microservices architectures to ensure reliable messaging between services. This pattern involves writing messages/events to an "outbox" in the same database transaction as the business data. A separate process then polls this outbox and publishes the messages to a message broker, ensuring that messages are only published once the business transaction has completed successfully.

![Transactional Outbox Pattern](/images/Architecture.jpeg)

## Problem Statement
In distributed systems, it's crucial to maintain consistency across services. A common challenge arises when a service attempts to perform "dual writes"—writing to a database and publishing a message/event to a message broker (like Kafka). If these operations are not atomic, it can lead to inconsistencies and data loss in case of a failure during one of these operations. Using `@Transactional` alone does not guarantee success as it does not cover the producer sending the message to the message broker.

## Our Solution
To address the dual write dilemma, our solution involves:
- Writing data to the database and adding a corresponding message to an outbox table within the same transaction.
- A separate Order Poller service polls this outbox for unprocessed messages and publishes them to Kafka. This ensures that every database transaction is paired with the eventual message publishing without risking data inconsistency.

## Project Components

### Order Service
The `order-service` is responsible for handling order requests and storing order data in a database, as well as recording events to an outbox table.

#### Key Dependencies
- `spring-boot-starter-data-jpa` for JPA functionalities.
- `spring-boot-starter-web` for RESTful endpoints.
- `mysql-connector-java` for MySQL database interactions.
- `lombok` for reducing boilerplate code.

#### Configuration
- Application and database connection settings in `application.properties`.
- Kafka topic and connection details.

#### Main Components
- `OrderController`: Exposes a POST endpoint for order creation.
- `OrderService`: Manages the business logic for order creation and writes to the outbox.
- `OrderRepository` & `OutboxRepository`: JPA repositories for data access.

### Order Poller
The `order-poller` service is responsible for polling the outbox table and publishing messages to Kafka.

#### Key Dependencies
- `spring-boot-starter-data-jpa` and `spring-kafka` for database operations and Kafka integration.
- `spring-boot-devtools` for enhanced development experience.
- `mysql-connector-java` for MySQL interactions.

#### Configuration
- Separate `application.properties` for service-specific configurations.
- Polling rate and Kafka topic configurations.

#### Main Components
- `OrderPollerService`: Scheduled task that polls the outbox table and publishes messages to Kafka.
- `MessagePublisher`: Handles the Kafka message publishing logic.

## Setup and Running the Application

### Prerequisites
- Apache Kafka and Zookeeper running locally.
- MySQL database server running locally.

### Steps to Run
1. Start Zookeeper and Kafka servers.
2. Initialize the MySQL database with the required schema.
3. Run the `order-service`:
   
   ```bash
   cd order-service
   mvn spring-boot:run
   ```
   
4. Run the `order-poller`:
   
   ```bash
   cd order-poller
   mvn spring-boot:run
   ```
   
5. Post an order to `http://localhost:8080/order/createOrder` and observe the output in Kafka and database.

## Desired Output
After posting an order, the order details should be visible in the database and the corresponding event message should be seen in the Kafka topic, indicating a successful implementation of the Transactional Outbox Pattern.

## Conclusion
This project showcases a robust implementation of the Transactional Outbox Pattern, ensuring consistent data management across distributed services in a microservices architecture.
