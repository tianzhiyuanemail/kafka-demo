package com.example.client.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

public class ConsumerSample {

    public static void main(String[] args) {

        send();
    }


    public static void send() {
        Properties props = new Properties();

        props.put("bootstrap.servers", "47.92.146.108:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);


        TopicPartition topicPartition0 = new TopicPartition("abcdef", 0);
        TopicPartition topicPartition1 = new TopicPartition("abcdef", 1);
        TopicPartition topicPartition2 = new TopicPartition("abcdef", 2);

//        consumer.subscribe(Arrays.asList("abcdef"));

        consumer.assign(Arrays.asList(topicPartition0));


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));


            for (ConsumerRecord<String, String> record : records) {
                System.out.println("----------------------------------------------[fetched from partition " + record.partition() + ", offset: " + record.offset() + ", message: " + record.key() + record.value() + "]");
            }
        }
    }

}