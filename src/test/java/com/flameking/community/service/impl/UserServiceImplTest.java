package com.flameking.community.service.impl;

import com.flameking.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {
  @Autowired
  UserService userService;

  @Test
  public void testSelectUserById(){
    log.debug("User:{}", userService.selectUserById(1).toString());
  }


}