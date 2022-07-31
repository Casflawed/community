package com.flameking.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CookieUtils {
  public static String getValue(HttpServletRequest request, String name){
    Cookie[] cookies = request.getCookies();
    if (cookies == null){
      return null;
    }else{
      for (Cookie cookie : cookies) {
        if (Objects.equals(cookie.getName(), name)) {
          return cookie.getValue();
        }
      }
      return null;
    }
  }
}
