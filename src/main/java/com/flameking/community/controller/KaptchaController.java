package com.flameking.community.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {
  @Autowired
  DefaultKaptcha kaptchaProducer;
  @GetMapping
  public void getKaptcha(HttpServletResponse response, HttpSession session){
    String text = kaptchaProducer.createText();
    BufferedImage image = kaptchaProducer.createImage(text);            //生成验证码
    session.setAttribute("kaptcha", text);                           //保存验证码
    try {
      response.setContentType("image/png");
      ServletOutputStream os = response.getOutputStream();
      ImageIO.write(image, "png", os);                      //响应验证码
    } catch (IOException e) {
      log.debug("验证码响应失败：{}", e.getMessage());
    }

  }
}
