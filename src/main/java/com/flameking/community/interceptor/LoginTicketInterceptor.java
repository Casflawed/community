package com.flameking.community.interceptor;

import com.flameking.community.entity.LoginTicket;
import com.flameking.community.entity.User;
import com.flameking.community.service.LoginTicketService;
import com.flameking.community.service.UserService;
import com.flameking.community.util.CookieUtils;
import com.flameking.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
  @Autowired
  private LoginTicketService loginTicketService;

  @Autowired
  private UserService userService;

  @Autowired
  private HostHolder hostHolder;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String ticket = CookieUtils.getValue(request, "ticket");
    if (ticket != null) {
      List<LoginTicket> loginTicketList = loginTicketService.findLoginTicketByTicket(ticket);
      if (!loginTicketList.isEmpty()) {
        LoginTicket loginTicket = loginTicketList.get(0);
        if (loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {                //凭证有效且过期时间在当前时间之后
          User loginUser = userService.findUserById(loginTicket.getUserId());
          hostHolder.setUser(loginUser);
        }
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    User user = hostHolder.getUser();
    if (user != null && modelAndView != null) {
      modelAndView.addObject("loginUser", user);
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    hostHolder.clear();
  }
}
