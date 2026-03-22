## Content-Based Routing

```bash
docker compose down
docker compose up
```

Start the **Digital Delivery Consumer** application.

```
SpringApplication.run(
        DigitalDeliveryConsumer.class, "--section=sec09", "--config=01-digital-consumer"
);
```

Start the **Physical Delivery Consumer** application.

```
SpringApplication.run(
        PhysicalDeliveryConsumer.class, "--section=sec09", "--config=02-physical-consumer"
);
```

Start the **Processor** application.

```
SpringApplication.run(
        Processor.class, "--section=sec09", "--config=03-processor"
);
```

Start the **Producer** application.

```
SpringApplication.run(
        Producer.class, "--section=sec09", "--config=04-producer"
);
```

**Observe:**

* The processor builds delivery objects for each order.
* Events are routed to two different consumers based on the product type.