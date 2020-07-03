package com.example.controller;


import com.example.producer.KafkaSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private KafkaSender kafkaSender;

    @RequestMapping("/a")
    public void a(Integer m) {
        kafkaSender.send(m);
        System.out.println(1);
    }




}