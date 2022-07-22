package com.flameking.community.service;

import com.flameking.community.entity.User;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
public interface UserService {

  /**
   * 根据用户id查询用户信息
   * @param id
   * @return 帖子对应的发帖人的信息
   */
  User findUserById(Integer id);

  /**
   * 注册用户信息
   * @param user
   * @return
   */
  Map<String, String> register(@Valid User user);

  /**
   * 通过用户名查找用户
   * @param username
   * @return
   */
  List<User> findUserByUsername(String username);

  List<User> findUserByEmail(String email);

  int activation(Integer id, String activeCode);
}
