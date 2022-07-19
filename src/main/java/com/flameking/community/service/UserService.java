package com.flameking.community.service;

import com.flameking.community.entity.User;

public interface UserService {
  User selectUserById(Integer id);
}
