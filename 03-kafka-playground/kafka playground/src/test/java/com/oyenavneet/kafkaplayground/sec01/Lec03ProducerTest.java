package com.oyenavneet.kafkaplayground.sec01;

import com.oyenavneet.kafkaplayground.sec19.ProducerApp;
import com.oyenavneet.kafkaplayground.sec19.dto.Order;
import com.oyenavneet.kafkaplayground.sec19.dto.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.kafka.support.KafkaHeaders;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        classes = ProducerApp.class,
        properties = {
                "section=sec19",
                "config=04-producer"
        }
)
public class Lec03ProducerTest extends AbstractTest {

    @Test
    public void orderProducerTest() {
        var message1 = this.receive(ORDER_EVENTS, Order.class);
        var message2 = this.receive(ORDER_EVENTS, Order.class);

        // we can not use receivedKey. We use test binder, and we test how the message is produced
        Assertions.assertEquals(1, message1.getHeaders().get(KafkaHeaders.KEY, Integer.class));
        Assertions.assertEquals(1, message1.getPayload().id());
        Assertions.assertEquals(ProductType.DIGITAL, message1.getPayload().productType());

        Assertions.assertEquals(2, message2.getHeaders().get(KafkaHeaders.KEY, Integer.class));
        Assertions.assertEquals(2, message2.getPayload().id());
        Assertions.assertEquals(ProductType.PHYSICAL, message2.getPayload().productType());
    }
}
