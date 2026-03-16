## Kafka Console Producer

Kafka provides a **console producer tool** that allows us to send messages to a topic directly from the command line. This is mainly used for **learning, testing, and debugging** purposes. Not meant for production workloads.

### Access the container
- Ensure that you are in the `/opt/kafka/bin` directory.

```
docker exec -it kafka bash
```

### Create a topic

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic demo-topic
```

### Produce messages to a topic

```bash
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

- The producer waits for input from the console
- **Each line** followed by pressing **Enter** is sent as a separate message
- Messages are sent in **plain text** by default
- Press `Ctrl + C` to stop the producer.


### Produced data is ready to be consumer by consumer in immediately if  -timeout 0, by default timeout value is 1000 ms
If set and the producer is running in
asynchronous mode, this gives the
maximum amount of time a message
will queue awaiting sufficient batch
size. The value is given in ms. This
is the option to control `linger.ms`
in producer configs. (default: 1000)
```bash
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic --timeout 0
```
