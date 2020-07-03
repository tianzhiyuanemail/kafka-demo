package com.example.sonsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafkaReceiver1 {
    @KafkaListener(topics = {"zhisheng"},groupId = "00000")
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("--------KafkaReceiver1--------- record =" + record);
            log.info("---------KafkaReceiver1--------- message =" + message);
        }
    }
}
