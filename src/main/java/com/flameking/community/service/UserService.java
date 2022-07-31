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
   *
   * @param user
   * @return errorMap的size=0，用户信息可以注册，否则用户信息已被注册
   */
  Map<String, String> register(@Valid User user);

  /**
   * 通过用户名查找用户
   * @param username
   * @return
   */
  List<User> findUserByUsername(String username);

  List<User> findUserByEmail(String email);

  /**
   * 登录，expired用户生成登录凭证的过期时间
   * @param username
   * @param password
   * @param expiredSeconds 过期时间
   * @return
   */
  Map<String, String> login(String username, String password, int expiredSeconds);

  /**
   * 激活账户
   * @param id
   * @param activeCode
   * @return
   */
  int activation(Integer id, String activeCode);

  /**
   * 更新用户头像url
   * @param id          用户id
   * @param headerUrl
   * @return
   */
  int updateHeaderUrlById(Integer id, String headerUrl);
}
