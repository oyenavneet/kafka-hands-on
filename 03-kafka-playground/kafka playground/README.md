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

## Kafka - Concurrent Message Processing  (Scaling Performance)
- When scaling with multiple Kafka brokers is not required, performance can be improved within a single node by utilizing multiple CPU cores through concurrency tuning.
- Spring Cloud Stream: Default Behavior
  - Spring Cloud Stream use **single-thread** message consumption.
  - Only one Kafka consumer is created per binding.
  - Messages are processed sequentially.

- **Improving Concurrency**
  - Increase the **concurrency level** to create multiple consumer threads
  - Allows parallel processing of messages from different partitions
  - Effective when topics have multiple partitions
  - Helps improve throughput without adding more Kafka nodes

- **Key Considerations**
  - Concurrency is limited by the number of partitions
  - Message ordering is maintained only within a partition
  - Higher concurrency may lead to unordered processing across partitions

---
**Note**  
Even if we set `consumer.concurrency = 3` (or any value), there is a high chance that the **producer publishes messages much faster** than the consumer can process them.
---

### ***To handle the above***:
- **Problem Context**
  - Producer publishing speed is very high
  - Processor/Consumer processing speed is low
  - Framework-level concurrency is NOT sufficient

### **Scenario 1: Message ordering is NOT important**
**Solution**:
- Use **unordered concurrent processing**
- Process messages in parallel using multiple threads (e.g., thread pools / virtual threads)
- Do not rely on partition-level ordering
- Maximize throughput by increasing parallelism at the application level
- Suitable for independent events (no ordering dependency)

### **Scenario 2: Message ordering IS important (based on a key)**
**Solution**:
- Use **key-based ordered processing**
- Ensure all messages with the same key are processed sequentially
- Route messages to the same processing thread/queue based on key (key-based partitioning or hashing)
- Maintain ordering within each key while still allowing parallelism across different keys
- Helps balance **ordering guarantees + performance**

## Kafka - Message Acknowledgment / Offset Commit
- **Acknowledgment** is an application concept.
- **Offset commit** is the kafka operation behind it.
- In Most of the cases we do not manually Acknowledgment it will be done by framework for us.
- It ensures data reliability by confirming message receipt between producers, brokers, and consumers
- Producer Acknowledgment (acks setting) 
  - acks=0 (None): The producer does not wait for any acknowledgment, prioritizing throughput over data safety, leading to high data loss risk.
  - acks=1 (Leader): The producer waits for the leader partition to confirm receipt. If the leader fails before replication, data might be lost.
  - acks=all or -1 (All Replicas): The producer waits for all in-sync replicas (ISRs) to acknowledge receipt, providing the highest durability at the cost of higher latency.
- Consumer Acknowledgment (Offset Management) 
  - Auto-Commit: Consumers automatically commit the highest offset periodically, ensuring low complexity but potential data duplication if processing fails mid-batch.
  - Manual Commit: Consumers explicitly call commitSync or commitAsync after processing a record, offering higher reliability and control.

## Manual Acknowledgement

Manual acknowledgement allows the application to explicitly control when a message is considered successfully processed.

### **Requirements**
- The consumer must receive messages as a **Message type** (to access metadata and headers)
  - ```Java
    @Bean
    Consumer<Message<OrderEvent>> orderEvent(){
        return msg -> "some implementation";
    }
    ```
- Spring cloud stream Consumer config YAML file we need to enable consumer:
  - ```yaml
      consumer:
        ack-mode: MANUAL
    ```
### **How it works**
- The application processes the message
- On successful processing → explicitly acknowledge the message
- If not acknowledged → offset is not committed and message can be retried

## Negative Acknowledgement (NACK)
- This is NOT a kafka concept. It is a framework feature
- Message is NOT acknowledged (Offset is not committed).
- Message is retried after a delay.
- Used for temporary / recoverable error.

```java
  var acknowledgement = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgement.class);
    try{
        // some code to process message
        acknowledgement.acknowledge();
    }catch(Exception e){
        acknowledgement.nack(Duration.ofSeconds(5));
    }
```
- In Batch Mode: for Manual Acknowledgment will we get single ACKNOWLEDGMENT for list/batch of events. we need to process all teh event in batch and then acknowledge.

## Error Handling & Fault Tolerance

### Error Handling Expectations

| Scenario                                          | Expected Behavior                                                                     |
|--------------------------------------------------|---------------------------------------------------------------------------------------|
| Message processed successfully                   | Acknowledge the message / Commit the offset                                           |
| Message processing fails                         | Do not commit the offset                                                              |
| Failure is retryable                             | Retry the message using a retry strategy:<br/> - Fixed delay<br/> - Exponential backoff |
| Failure is not retryable (invalid / corrupt data)| Skip retry, commit the offset, and publish details to DLQ/DLT                         |
| Retry attempts exhausted                         | Stop retrying, commit the offset, and publish details to DLQ/DLT                      |

---

### Error Handling in Spring Cloud Stream

- Supports **retry mechanisms** for transient failures
- Allows configuration of:
  - Retry attempts
  - Backoff strategies (fixed / exponential)
- Integrates with **Dead Letter Queue / Topic (DLQ/DLT)** for failed messages
- Helps ensure reliability without losing data

---

### Dynamic Pause & Resume of Bindings

- Useful when a consumer service (e.g., pod) is temporarily down or under heavy load
- Spring Cloud Stream provides **BindingsLifecycleController** to manage bindings

#### Key Capabilities:
- **Pause** → Temporarily stop message consumption
- **Resume** → Continue consumption from the last committed offset

#### Benefit:
- Ensures the consumer resumes processing **from where it left off** without data loss

