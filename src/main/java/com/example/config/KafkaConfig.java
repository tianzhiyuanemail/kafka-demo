package com.example.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("topic-name")
    private String topicName1 ;

    @Bean
    public NewTopic topic1() {
        return new NewTopic(topicName1, 2, (short) 2);
    }
}