package com.flameking.community.controller;

import com.alibaba.fastjson.JSON;
import com.flameking.community.entity.DiscussPost;
import com.flameking.community.entity.User;
import com.flameking.community.service.DiscussPostService;
import com.flameking.community.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class HomeController {
  @Autowired
  private DiscussPostService discussPostService;
  @Autowired
  private UserService userService;

  /**
   * 向index页传递帖子的页面信息和每条帖子与用户的映射，
   * 不足：pageInfo包含的List数据是冗余的
   * @param model
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/index")
  public String toIndexPage(Model model,
                            //pageNum从1开始，pageHelper会自动处理-1
                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
    ArrayList<HashMap<String, Object>> discussPostAndUserMaps = new ArrayList<>();
    PageInfo<DiscussPost> discussPostPageInfo =
            discussPostService.getDiscussPostPage(pageNum, pageSize);   //封装页面数据

    for (DiscussPost discussPost : discussPostPageInfo.getList()) {       //初始化帖子与用户的映射关系
      HashMap<String, Object> map = new HashMap<>();

      User user = userService.findUserById(discussPost.getUserId());
      map.put("user", user);
      map.put("post", discussPost);

      discussPostAndUserMaps.add(map);
    }

    String pageInfoJsonString = JSON.toJSONString(discussPostPageInfo); //如果直接传递Java对象，js无法识别
    model.addAttribute("maps", discussPostAndUserMaps);
    model.addAttribute("pageInfo", pageInfoJsonString);

    return "index";
  }

}
