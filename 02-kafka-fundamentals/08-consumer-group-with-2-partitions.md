## Consumer Group Demo (Topic with 2 Partitions)

### Prerequisite

* Ensure you have completed the **previous demo**
* The topic `order-events` must already exist **with 2 partitions**

---

### Start a Consumer Group with Two Consumers

We will start **two consumers in the same consumer group**.
Each consumer will run in a **separate terminal**.

#### Terminal 1

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property print.offset=true \
  --property print.key=true \
  --group payment-service
```

#### Terminal 2

```bash
docker exec -it kafka bash

./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property print.offset=true \
  --property print.key=true \
  --group payment-service
```

* Both consumers belong to the **same consumer group**
* Kafka will distribute partitions between them

### Start a Console Producer

Open **another terminal** and access the Kafka container:

```bash
docker exec -it kafka bash
```

Start the console producer.
We will send messages **with a key**, using the format `key:value`
(`:` is used as the key separator).

```bash
./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property parse.key=true \
  --property key.separator=:
```

### Observe

* Produce multiple messages
* Notice how messages are **distributed across the two consumers**
* Each partition is consumed by **only one consumer in the group**


## Partition Rebalancing

* In Case when you have a topic with 2 partitioning and only one consumer
* Kafka will assign both partition to same consumer (one consumer)
* If a new consumer instance appear then Kafka will perform Partition Rebalancing and if new item get produce by producer then it will distribute the item between both consumer based on the key
* In case of one consumer down then it again perform Partition Rebalancing and assign all item to remaining consumer
* If a topic having 2 partitioning and there iws 4 consumer then 1 consumer will stay idle
* 1 partition = 1 consumer

## Alter/Increase number of Partition of a topic

```bash
./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic order-events \
    --alter \
    --partitions 4

# we can only increase the partitions. not able to decrease
```

* Increase number of Partition of a topic could cause loss of message/ordering issue due to Partition Rebalancing

### How to handle solve it
* Design upfront properly
* Accept the message ordering issue for a short period of time
* Stop the producer. Drain th existing partitions. Start the producer.
* Create a new topic with design no of partitions
    * LeT the producer send message to the new topic
    * Let the consumers consume new tipi one they have processed old topic message