package com.example.client.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerSample {

    public static void main(String[] args) {

        send();
    }


    public static void send() {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.92.146.108:9092");

        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        /**
         * ProducerConfig.CLIENT_ID_CONFIG=client.id属性： 设置KafkaProducer对应的客户端id
         * 默认为""
         * 如果客户端不设置，那么kafka会自动生成一个：形式如 producer-1，producer-2 等
         */
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

        Producer<String, String> producer = new KafkaProducer<>(props);

        int i = 0;

        //3. 发送消息
        while (true) {
            i++;
            ProducerRecord<String, String> record = new ProducerRecord<>("abcdef", "k" + i, "v" + i);

            producer.send(record);
            System.out.println(".....................");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}