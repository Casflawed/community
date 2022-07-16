package com.flameking.community.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DiscussPost {

    private Integer id;


    private String userId;


    private String title;


    private Integer type;


    private Integer status;


    private Date createTime;


    private Integer commentCount;


    private Double score;


    private String content;
}