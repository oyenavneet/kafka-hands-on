package com.oyenavneet.kafkaplayground.sec01;


import com.oyenavneet.kafkaplayground.sec19.PhysicalDeliveryConsumerApp;
import com.oyenavneet.kafkaplayground.sec19.dto.PhysicalDelivery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.messaging.support.MessageBuilder;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        classes = PhysicalDeliveryConsumerApp.class,
        properties = {
                "section=sec19",
                "config=02-physical-consumer"
        }
)
public class Lec01PhysicalConsumerTest extends AbstractTest{

    @Test
    public void physicalConsumer(CapturedOutput output) {
        var physicalDelivery = new PhysicalDelivery(1, "xyz Lucknow", "Uttar Pradesh");
        var message = MessageBuilder.withPayload(physicalDelivery).build();
        this.inputDestination.send(message, PHYSICAL_DELIVERY);
        Assertions.assertTrue(output.getOut().contains("received: " + physicalDelivery));
    }
}
