package com.example.client.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerFastClient {

    public static final String brokerList = "47.92.146.108:9092";
    public static final String topic = "abcdef";

    public static final String groupId = "group.demo";

    public static Properties initProperties(){
        //0. 配置客户端的参数
        Properties prop = new Properties();

        /**
         * 消费者 的key 反序列化器，必须和生产者一致
         */
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /**
         * 消费者 的value 反序列化器，必须和生产者一致
         */
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /**
         * 0.1 设置broker服务端ip列表
         * ProducerConfig.BOOTSTRAP_SERVERS_CONFIG 属性：指定生产者连接的kafka集群的地址
         * 格式： host1:port1,host2:port2,host3:port3
         * 可以设置一个或者多个地址，中间以逗号分隔。此参数默认为 ""
         * 这里并不需要填写所有的kafka集群的所有broker的地址，因为消费者会从给定的broker查找到其他的broker的信息
         * 建议至少设置两个或两个以上的broker地址，当其中一个宕机的时候，生产者仍然可以连接到kafka集群
         */
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        /**
         * 0.2 设置消费组的名称,默认为""
         * 一般该参数设置为 具有业务的值
         */
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        /**
         * ProducerConfig.CLIENT_ID_CONFIG=client.id属性： 设置KafkaConsumer对应的客户端id
         * 默认为""
         * 如果客户端不设置，那么kafka会自动生成一个：形式如 consumer-1，consumer-2 等
         */
        prop.put(ConsumerConfig.CLIENT_ID_CONFIG, "client-id-demo");
        return prop;
    }

    public static void main(String[] args) {
        //0. 配置客户端的参数
        Properties prop = initProperties();

        //1. 创建一个消费客户端实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);


        //2. 订阅主题
        consumer.subscribe(Collections.singletonList(topic));

        //3. 循环消费消息
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("--------------->:" + record.value());
            }
        }

    }
}