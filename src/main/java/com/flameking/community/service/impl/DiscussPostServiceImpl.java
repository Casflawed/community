package com.flameking.community.service.impl;

import com.flameking.community.entity.DiscussPost;
import com.flameking.community.mapper.DiscussPostMapper;
import com.flameking.community.service.DiscussPostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiscussPostServiceImpl implements DiscussPostService {
  @Autowired
  private DiscussPostMapper discussPostMapper;


  @Override
  public PageInfo<DiscussPost> getDiscussPostPage(Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);        //开启分页查询

    PageInfo<DiscussPost> discussPostPageInfo = new PageInfo<>(discussPostMapper.selectDiscussPosts());       //pageHelper自动分页

    return discussPostPageInfo;
  }
}
