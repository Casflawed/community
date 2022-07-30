package com.flameking.community.controller;

import com.flameking.community.mapper.UserMapper;
import com.flameking.community.pojo.LoginDTO;
import com.flameking.community.service.LoginTicketService;
import com.flameking.community.service.UserService;
import com.flameking.community.support.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {
  @Autowired
  private UserService userService;

  @Autowired
  private LoginTicketService loginTicketService;

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @GetMapping("/login/page")
  public String getLoginPage(Model model) {

    return "site/login";
  }

  @PostMapping("/login")
  public String login(Model model, @Valid LoginDTO loginDTO, HttpSession session, HttpServletResponse response) {
    if (!loginDTO.getVerifycode().equalsIgnoreCase(session.getAttribute("kaptcha").toString())) {
      model.addAttribute("verifycode", "验证码错误");
      return "site/login";
    }
    int expiredSeconds = loginDTO.getRememberMe() ? EXPIREDSECONDS : DEFAULT_EXPIREDSECONDS;

    Map<String, String> map = userService.login(loginDTO.getUsername(), loginDTO.getPassword(), expiredSeconds);
    if (map.containsKey("ticket")) {                                //登录成功并且返回凭证
      Cookie cookie = new Cookie("ticket", map.get("ticket"));
      cookie.setPath(contextPath);                                  //cookie的有效范围
      cookie.setMaxAge(expiredSeconds);                             //cookie的过期时间
      response.addCookie(cookie);
      return "redirect:/index";
    }else{
      model.addAttribute("username", map.get("username"));
      model.addAttribute("password", map.get("password"));
      return "site/login";
    }

  }

  @GetMapping("/logout")
  public String logout(@CookieValue("ticket") String ticket){
    loginTicketService.logout(ticket);
    return "redirect:/login/page";
  }
}
