package com.flameking.community.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class MailClient {
  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String from;

  /**
   * 发送邮件
   * @param to        接收人
   * @param subject   邮件标题
   * @param content   邮件正文
   */
  public void sendMail(String to, String subject, String content){
    try {
      MimeMessage message = mailSender.createMimeMessage();         //创建消息，此时为空
      MimeMessageHelper helper = new MimeMessageHelper(message);    //使用helper设置邮件内容
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);                          //开启html解析，这样就可以通过thymeleaf模板发送html邮件了
      mailSender.send(helper.getMimeMessage());                     //发送邮件
      log.debug("发送邮件成功");

    } catch (MessagingException e) {
      log.debug("发送邮件失败：{}", e.getMessage());
    }

  }

}
