package com.example.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;    //id
    private Integer msg; //消息
    private Date sendTime;  //时间戳
}