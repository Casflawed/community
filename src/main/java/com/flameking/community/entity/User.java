package com.flameking.community.entity;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class User {

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;


    private String salt;

    @NotBlank(message = "邮箱不能为空")
    private String email;


    private Integer type;


    private Integer status;


    private String activationCode;


    private String headerUrl;


    private Date createTime;

}