package com.flameking.community.mapper;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

  @Autowired
  UserMapper userMapper;

  @Test
  public void testSelectByExample(){
    log.debug("users:{}",userMapper.selectByExample(null));
  }
}