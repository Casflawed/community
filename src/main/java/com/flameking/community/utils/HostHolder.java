package com.flameking.community.utils;

import com.flameking.community.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
  private ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

  public void setUser(User user){
    userThreadLocal.set(user);
  }

  public User getUser(){
    return userThreadLocal.get();
  }

  public void clear(){
    userThreadLocal.remove();
  }
}
