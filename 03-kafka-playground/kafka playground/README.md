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

## Kafka - Batch Processing
- Batch processing improves throughput by reducing per-message overhead.
- Sending messages to kafka or consuming messages one by one are network call so avoid message one by one
- Instead, sending/consumer message in a batch significantly improve the performance

****Producing Message in Batches****
- Kafka client library buffers records in memory and send them together based on the configurations.
- Below are some properties which we tune for batch processing of messages in kafka
  - ***linger.ms*** → How long (in ms) the producer waits for more message to arrive before sending in batch. (Default value is 0) 
  - ***batch.size*** → The maximum size (in bytes) of sing batch. (Default size is 16 KB)
  - ***compression.type*** → The algorithm the producer use to shrink data before sending it over the network (Default value is none)
    - **Iz4** → Very fast + Moderate compression
    - **zstd** → Fast + High compression. Good for large message
    - **gzip** → Slow + Max compression
    - **snappy** → Similar to LZ4. Legacy

****Consuming Message in Batches****
- Below are some properties which we tune for batch processing of messages in kafka
  - ***max.poll.records*** → The maximum number if records returned in a single call. Upper limit (Not to receive too much). (Default:1)
  - ***fetch.min.bytes*** → The minimum amount of data (in bytes) the broker must collect before sending it to the consumer. Lowe limit. (Default:1).
  - ***fetch.max.wait.ms*** → The deadline for the broker to wait for ***fetch.min.bytes***. (Default:500)
- Tuning above properties make the kafka broker and consumer application more efficient 
- However, spring cloud stream will be delivering messages one by one to our function 
- Kafka consumer fetches records in batched.
- The message handler process one message at a time, and each message results in an individual database call.
- Kafka batch consumption does NOT automatically translate to batch database writes.
- To enable ***End-to-End Batching*** along with above tuning properties we have  exposed our consumer bean like 
- ```java
  @Bean
  Consumer<List<String>> consumer(){
    return list -> {
        // some code
        // repository.saveAll(entities)
    };
  }
  ```
- Also need to configure ***batch-mode: true*** in out consumer binding configuration yaml/properties file