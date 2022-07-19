package com.flameking.community.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DiscussPost {
    private Integer id;

    private Integer userId;

    private String title;

    private Integer type;

    private Integer status;

    @DateTimeFormat
    @JsonFormat
    @JSONField
    private Date createTime;

    private Integer commentCount;

    private Double score;

    private String content;
}