package com.flameking.community.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CookieUtil {
  public static String getValue(HttpServletRequest request, String name){
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (Objects.equals(cookie.getName(), name)){
        return cookie.getValue();
      }
    }
    return null;
  }
}
