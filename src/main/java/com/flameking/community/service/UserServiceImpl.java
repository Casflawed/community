package com.flameking.community.service;

import com.flameking.community.entity.User;
import com.flameking.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserMapper userMapper;

  @Override
  public User selectUserById(Integer id) {
    return userMapper.selectByPrimaryKey(id);
  }
}
