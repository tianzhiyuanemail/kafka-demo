package com.example.client.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


public class ProducerFastStart {

    public static final String brokerList = "47.92.146.108:9092";
    public static final String topic = "abcdef";

    public static Properties initProperties(){
        Properties prop = new Properties();

        /**
         * ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG属性：指定 key的序列化器
         */
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        /**
         * ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG属性：指定 value 的序列化器
         */
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /**
         * ProducerConfig.BOOTSTRAP_SERVERS_CONFIG 属性：指定生产者连接的kafka集群的地址
         * 格式： host1:port1,host2:port2,host3:port3
         * 可以设置一个或者多个地址，中间以逗号分隔。此参数默认为 ""
         * 这里并不需要填写所有的kafka集群的所有broker的地址，因为生产者会从给定的broker查找到其他的broker的信息
         * 建议至少设置两个或两个以上的broker地址，当其中一个宕机的时候，生产者仍然可以连接到kafka集群
         */
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        //prop.put("broker.id",0);

        /**
         * ProducerConfig.CLIENT_ID_CONFIG=client.id属性： 设置KafkaProducer对应的客户端id
         * 默认为""
         * 如果客户端不设置，那么kafka会自动生成一个：形式如 producer-1，producer-2 等
         */
        prop.put(ProducerConfig.CLIENT_ID_CONFIG,"producer.client.id.demo");
        return prop;
    }

    public static void main(String[] args) {
        //0. 配置参数
        Properties prop = initProperties();

        //1. 创建kafka的客户端，并配置参数
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);

        /**
         * 1.1 创建kafka的客户端 并指定 key和value所使用的 序列化 类
         */
        //KafkaProducer<String, String> producer = new KafkaProducer<>(prop,new StringSerializer(),new StringSerializer());

        //2. 创建待发送的消息记录
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "Hello,World");

        //3. 发送消息
        while (true){
            producer.send(record);
            System.out.println(".....................");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //4. 关闭资源
        //producer.close();

    }
}