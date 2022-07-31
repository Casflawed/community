package com.flameking.community.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class MailClientTest {

  @Autowired
  MailClient mailClient;

  @Autowired
  TemplateEngine templateEngine;

  @Test
  public void testSendTextMail() {
    mailClient.sendMail("2711554770@qq.com", "test", "test内容");
  }

  @Test
  public void testSendHtmlMail(){
    Context context = new Context();

    context.setVariable("username", "大帅哥");

    String content = templateEngine.process("/mail/mail-template", context);
    System.out.println(content);

    mailClient.sendMail("2711554770@qq.com", "我才是标题", content);
  }
}