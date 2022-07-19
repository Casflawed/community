package com.flameking.community.service;

import com.flameking.community.entity.DiscussPost;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DiscussPostService {
  /**
   * 查询帖子的分页数据
   * @param pageNum 页码
   * @param pageSize 每页的记录数量
   * @return
   */
  PageInfo<DiscussPost> getDiscussPostPage(Integer pageNum, Integer pageSize);

}
