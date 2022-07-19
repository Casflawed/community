package com.flameking.community.pojo;

import java.util.List;

/**
 * page 分页模型
 */
public class Page {
  public static final int PAGE_CAPACITY = 4;
  // 当前页码
  private Integer pageNum;
  // 总页码
  private Integer pagesTotal;
  // 当前页显示数量
  private Integer pageSize = PAGE_CAPACITY;
  // 总记录数
  private Integer itemTotalCount;

  // 处理页面的前后台 url
  private String url;

}
