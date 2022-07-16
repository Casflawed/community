package com.flameking.community.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private Integer id;


    private Integer userId;


    private Integer entityType;


    private Integer entityId;


    private Integer targetId;


    private Integer status;


    private Date createTime;


    private String content;
}