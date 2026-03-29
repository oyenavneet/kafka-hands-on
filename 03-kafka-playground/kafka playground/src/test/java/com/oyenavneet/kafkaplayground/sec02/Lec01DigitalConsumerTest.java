package com.oyenavneet.kafkaplayground.sec02;


import com.oyenavneet.kafkaplayground.sec19.DigitalDeliveryConsumerApp;
import com.oyenavneet.kafkaplayground.sec19.dto.DigitalDelivery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.messaging.support.MessageBuilder;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        classes = DigitalDeliveryConsumerApp.class,
        properties = {
                "section=sec19",
                "config=01-digital-consumer"
        }
)

public class Lec01DigitalConsumerTest extends AbstractTest {

    @Test
    public void digitalConsumer(CapturedOutput output) {
        var digitalDelivery = new DigitalDelivery(1, "sam@gmail.com");
        var message = MessageBuilder.withPayload(digitalDelivery).build();
        this.streamBridge.send(DIGITAL_DELIVERY, message);
        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> Assertions.assertTrue(output.getOut().contains("received: " + digitalDelivery)));
    }

}
