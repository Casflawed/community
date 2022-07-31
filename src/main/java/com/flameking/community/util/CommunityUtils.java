package com.flameking.community.util;

import org.springframework.util.DigestUtils;

import java.util.Objects;
import java.util.UUID;

public class CommunityUtils {
  /**
   * 生成随机字符串
   * @return
   */
  public static String generateUUID(){
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  /**
   * 封装md5加密算法
   * @param key
   * @return
   */
  public static String md5(String key){
    if (key == null || Objects.equals(key.trim(), "")){
      return null;
    }
    return DigestUtils.md5DigestAsHex(key.getBytes());
  }
}
