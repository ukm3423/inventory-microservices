package com.notificationservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.basedomain.dto.StudentEvent;


@Service
public class UserResponseProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResponseProducer.class);

    @Autowired
    private KafkaTemplate<String , StudentEvent> kafkaTemplate;

    @Autowired
    private NewTopic responseTopicName;
    
     public void sendResponse(StudentEvent event) {
        LOGGER.info(String.format("\n\nSending response from notification-service => %s", event.toString()));

        Message<StudentEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, responseTopicName.name())
                .build();

        kafkaTemplate.send(message);
    }

}
