package com.flameking.community.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * fastjson配置
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    FastJsonConfig config = new FastJsonConfig();
    config.setDateFormat("yyyy-MM-dd");
    config.setCharset(StandardCharsets.UTF_8);
    config.setSerializerFeatures(
            SerializerFeature.WriteClassName,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.PrettyFormat,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullStringAsEmpty
    );
    converter.setFastJsonConfig(config);
    converters.add(0, converter);                 //相比与Jackson优先使用fastjson
  }
}
