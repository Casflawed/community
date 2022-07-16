package com.flameking.community.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private Integer id;


    private Integer fromId;


    private Integer toId;

    private String conversationId;


    private Integer status;


    private Date createTime;


    private String content;

}