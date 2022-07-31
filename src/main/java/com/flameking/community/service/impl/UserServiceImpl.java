package com.flameking.community.service.impl;

import com.flameking.community.entity.LoginTicket;
import com.flameking.community.entity.User;
import com.flameking.community.entity.UserExample;
import com.flameking.community.mapper.LoginTicketMapper;
import com.flameking.community.mapper.UserMapper;
import com.flameking.community.service.UserService;
import com.flameking.community.support.CommunityConstant;
import com.flameking.community.util.CommunityUtils;
import com.flameking.community.util.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;


@Service
public class UserServiceImpl implements UserService, CommunityConstant {
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private LoginTicketMapper loginTicketMapper;
  @Autowired
  private MailClient mailClient;            //邮件客户端

  @Autowired
  private TemplateEngine templateEngine;    //生成html邮件字符串

  //域名和contextPath主要用于生成邮件中的链接
  @Value("${community.path.domain}")
  private String domain;
  @Value("${server.servlet.context-path}")
  private String contextPath;


  @Override
  public User findUserById(Integer id) {
    return userMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<User> findUserByUsername(String username) {
    UserExample userExample = new UserExample();
    userExample.createCriteria().andUsernameEqualTo(username);
    return userMapper.selectByExample(userExample);
  }

  @Override
  public List<User> findUserByEmail(String email) {
    UserExample userExample = new UserExample();
    userExample.createCriteria().andEmailEqualTo(email);
    return userMapper.selectByExample(userExample);
  }

  @Override
  public Map<String, String> login(String username, String password, int expiredSeconds) {
    HashMap<String, String> map = new HashMap<>();
    List<User> userList = findUserByUsername(username);
    if (userList.isEmpty()) {                            //用户名不存在
      map.put("username", "用户名错误");
      return map;
    }
    User user = userList.get(0);
    if (!Objects.equals(CommunityUtils.md5(password), user.getPassword())) {               //密码正确、登录成功，生成登录凭证
      map.put("password", "密码错误");
      return map;
    }

    LoginTicket loginTicket = new LoginTicket();
    loginTicket.setUserId(user.getId());
    loginTicket.setTicket(CommunityUtils.generateUUID());
    loginTicket.setStatus(0);                                                             //生成有效的登录凭证
    loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));        //过期时间换算成秒

    loginTicketMapper.insert(loginTicket);
    map.put("ticket", loginTicket.getTicket());
    return map;
  }


  @Override
  public Map<String, String> register(User user) {
    if (user == null) {
      throw new IllegalArgumentException("user参数不能为空");                                  //这个应该捕获并在前端跳转到错误页面
    }

    HashMap<String, String> map = new HashMap<>();                                          //存放提示信息

    List<User> users = findUserByUsername(user.getUsername());
    if (!users.isEmpty()) {
      map.put("username", "用户名已经被注册");
    }
    users = findUserByEmail(user.getEmail());
    if (!users.isEmpty()) {
      map.put("email", "邮箱已经被注册");
    }

    if (map.isEmpty()) {                                                                     //用户信息可以注册
      user.setSalt(CommunityUtils.generateUUID().substring(0, 5));
      user.setPassword(CommunityUtils.md5(user.getPassword() + user.getSalt()));         //对密码加密，密码+盐值：提高安全性

      user.setType(0);
      user.setStatus(0);
      user.setActivationCode(CommunityUtils.generateUUID());
      user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));                                          //随机头像
      user.setCreateTime(new Date());
      userMapper.insert(user);

      Context context = new Context();
      context.setVariable("email", user.getEmail());                                    //接收着我邮箱
      context.setVariable("url", domain + contextPath + "/register/activation/" +
              user.getId() + "/" + user.getActivationCode());                                 //生成激活链接
      mailClient.sendMail(user.getEmail(), "激活账号", templateEngine.process("/mail/activation", context));

      return map;
    }

    return map;
  }


  @Override
  public int activation(Integer id, String activeCode) {
    User user = userMapper.selectByPrimaryKey(id);
    if (user != null) {
      if (user.getStatus() == 1) {                                                    //重复激活
        return ACTIVATION_REPEAT;
      } else if (Objects.equals(activeCode, user.getActivationCode())) {               //激活成功
        user.setStatus(1);
        userMapper.updateByPrimaryKey(user);
        return ACTIVATION_SUCCESS;
      } else {                                                                         //激活失败
        return ACTIVATION_FAILURE;
      }
    }
    return ACTIVATION_FAILURE;
  }

  @Override
  public int updateHeaderUrlById(Integer id, String headerUrl) {
    userMapper.updateHeaderUrlById(id, headerUrl);
    return 0;
  }


}
