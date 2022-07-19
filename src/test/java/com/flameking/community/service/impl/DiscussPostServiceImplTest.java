package com.flameking.community.service.impl;

import com.flameking.community.entity.DiscussPost;
import com.flameking.community.service.DiscussPostService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@SpringBootTest
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class DiscussPostServiceImplTest {
  @Autowired
  DiscussPostService discussPostService;

  @Test
  public void testSelectDiscussPosts() {
    PageInfo<DiscussPost> discussPostPage = discussPostService.getDiscussPostPage(2, 10);
    log.debug("帖子分页:{}", discussPostPage);
    List<DiscussPost> list = discussPostPage.getList();
    for (DiscussPost discussPost : list) {
      System.out.println(discussPost);
    }
  }

}