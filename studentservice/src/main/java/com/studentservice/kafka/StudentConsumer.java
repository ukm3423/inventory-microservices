package com.studentservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StudentConsumer {
    private Logger LOGGER = LoggerFactory.getLogger(StudentConsumer.class);

    @KafkaListener(topics = "${spring.kafka.response-topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeResponse(Object obj) {

        LOGGER.info(String.format("\nResponse received from notification-service => %s", obj.toString()));

        // Handle the response event (e.g., update the order status in the database)
    }

    // private void updateOrderStatus(Object event) {
    //     // Your logic to update the order status in the database
    //     // e.g., orderRepository.updateStatus(event.getOrder().getOrderId(),
    //     // event.getStatus());
    // }
}
