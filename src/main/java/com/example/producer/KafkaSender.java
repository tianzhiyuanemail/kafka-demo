package com.example.producer;

import com.example.pojo.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class KafkaSender {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    //发送消息方法
    public void send(Integer m) {
        Long s = System.currentTimeMillis();
        Message message = new Message();
        message.setId(s);
        message.setMsg(m);
        message.setSendTime(new Date());

//        log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("zhisheng",  "" + s, gson.toJson(message));

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("===Producing message success");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("===Producing message failed");
            }

        });



    }
}