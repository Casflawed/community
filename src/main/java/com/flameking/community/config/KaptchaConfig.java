package com.flameking.community.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
  @Bean
  DefaultKaptcha producer() {
    Properties properties = new Properties();
    properties.put("kaptcha.image.width", "100");
    properties.put("kaptcha.image.height", "40");
    properties.put("kaptcha.textproducer.font.size", "30");
    properties.put("kaptcha.textproducer.font.color", "black");
    properties.put("kaptcha.textproducer.char.string", "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    properties.put("kaptcha.textproducer.char.length", "4");
    properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");                                      //使用默认的毛玻璃效果预防机器识别验证码，就不加noise了

    DefaultKaptcha kaptcha = new DefaultKaptcha();     //默认的实现
    Config config = new Config(properties);
    kaptcha.setConfig(config);

    return kaptcha;
  }

}
