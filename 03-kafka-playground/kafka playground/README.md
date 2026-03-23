# Kafka Hand-on in Spring Boot (Cloud Stream)

## Spring Cloud Stream
A spring module / framework for building event-driven/message-driven microservices

##  Event-Driven Application Types
- Producer
  - Publishes events to a messaging system
  - Does not know who consumes the events
- Consumer
  - Consumer events
  - Does not know who produced the events
- Processor
  - Consumes events, Processes and Produces new events
  - Consumer + Produce

## Kafka Message Attributes
- Kafka message consists of payload (event) + metadata
- Core Attributes
  - Key
  - Value (payload)
  - Headers
  - Timestamp (event time

## StreamBridge
- Spring component provided by Spring Cloud Stream
- Enables imperative, programmatic message sending
- Used when the Supplier / Function model is not sufficient
- Can send messages to any configured binding at runtime

## Reactive Consumer
- Spring Cloud Stream supports Reactive Programming.
- Consumer/Producer functions can use Flux<T> / Mono<T>.
- Supports backpressure within the message processing pipeline.
- Note:
  - There is no reactive Kafka binder
  - Processing runs on dedicated Kafka consumer threads, not event-loop threads
  - Safe to use with Spring WebFlux / Spring Data R2DBC etc

## Reactive Producer - Supplier<Flux<T>>
- Flux<T> represents a stream of T.
  - Polling configuration is not required.
  - Framework internally subscribes to the Flux


## Event-Driven Application Types - Processor -  Processing Patterns
A processor application can emit zero, one, or multiple events for each event it consumes

- 1 → 1 → Mapping Processor (One to one)
- 1 → 0 / 1 → Filter Processor → A Function<T, R> can return null to filter out a message.
- 1 → N →  N Mapping Processor
  - Case 1: Returning a List<T>
    - Function<Order, List<Notification>>
  - Spring Cloud Stream interpretation
    - Function returns ONE value
    - That value is List<Notification>
    - Binder sends it as:
      - Message<List<Notification>> (single kafka record
  - Case 2: Returning a List<Message<T>>
    - Function<Order, List<Message<Notification>>>
  - Spring Cloud Stream interpretation
    - Function returns a collection of Message objects
    - Binder treats each Message as independent
    - Result:
      - Message<Notification>
      - Message<Notification>
      - Message<Notification>   (multiple Kafka records)

## Event Routing
Process of directing an event to one or more destination based on rules such as event content or runtime conditions.
Type:
- Content-based → Process of directing an event to one or more destinations based on rules such as event content or runtime conditions. (sec09)
- Dynamic → Process of directing an event to one or more destinations based on rules such as event content or runtime conditions. (sec10)

## Kafka Cluster

- **High Availability**: Data is replicated across multiple brokers to ensure fault tolerance and minimal downtime.
  - Each partition has a **leader** and one or more **followers**
  - Partitions are replicated across brokers based on the **replication factor**
  - If a leader fails, a follower is automatically promoted to leader
  - Ensures no data loss and continuous availability
  - **Replication** is the key factor for availability

- **Horizontal Scalability**: Kafka scales by distributing data and load across multiple brokers and partitions.
  - A topic is divided into multiple partitions
  - Partitions are distributed across different brokers in the cluster
  - Producers can write to multiple partitions in parallel
  - Consumers in a consumer group can read from partitions in parallel
  - Adding more brokers increases system capacity
  - Increasing partitions improves parallelism and throughput
  - **Brokers** provide capacity
  - **Partitions** provide scalability

- **Kafka Roles (`process.roles`)**:
  - **Broker** → Handles read and write operations
  - **Controller** → Manages cluster metadata and coordination
  - **Broker + Controller** → Can perform both roles