package com.notificationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    
    @Value("${spring.kafka.response-topic.name}")
    private String responseTopic;

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(responseTopic).build();
    }
}
