package com.studentservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.basedomain.dto.UserDTO;

@Service
public class StudentProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentProducer.class);

    @Autowired
    private NewTopic topic;

    @Autowired
    private KafkaTemplate<String, UserDTO> kafkaTemplate;

    // public void sendMessage(StudentEvent event) {
    // LOGGER.info(String.format("\nStudent Event Send to the Notification Service
    // => %s", event.toString()));

    // event.setMessage("Student Verification is PENDING state .....");
    // // CREATING MESSAGE
    // Message<StudentEvent> message = MessageBuilder
    // .withPayload(event)
    // .setHeader(KafkaHeaders.TOPIC, topic.name())
    // .build();

    // kafkaTemplate.send(message);

    // }

    public void sendMessage(UserDTO event) {
        LOGGER.info(String.format("\nStudent Send to the AuthService => %s", event.toString()));

        event.setMessage("Student Verification is PENDING state .....");
        // CREATING MESSAGE
        Message<UserDTO> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);

    }
}
