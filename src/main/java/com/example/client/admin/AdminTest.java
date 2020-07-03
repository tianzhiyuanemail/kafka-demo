package com.example.client.admin;

import org.apache.kafka.clients.admin.*;

import java.util.*;

public class AdminTest {

    private static AdminClient adminClient;

    public static void main(String[] args) throws Exception {

        createClient();

         createTopic();
//        deleteTopic();
        listTopics();

    }


    public static void listTopics() throws Exception {
        ListTopicsResult listTopicsResult = adminClient.listTopics();

        Set<String> strings = listTopicsResult.names().get();

        System.out.println(strings);
    }


    public static void createTopic() throws Exception {
        short s = 1;
        List<NewTopic> newTopics = Collections.singletonList(new NewTopic("abcdef", 3, s));
        CreateTopicsResult topics = adminClient.createTopics(newTopics);
        System.out.println(topics.all().get());
    }

    public static void deleteTopic() throws Exception {
        short s = 1;
        List<String> newTopics = Collections.singletonList("abcdef");
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(newTopics);
        System.out.println(deleteTopicsResult.all().get());
    }

    public static void createClient() {
        Properties props = new Properties();
        props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "47.92.146.108:9092");
        adminClient = AdminClient.create(props);
    }

}