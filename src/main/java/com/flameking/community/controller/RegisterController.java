package com.flameking.community.controller;

import com.flameking.community.entity.User;
import com.flameking.community.service.UserService;
import com.flameking.community.support.CommunityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController implements CommunityConstant {
  @Autowired
  private UserService userService;

  /**
   * 返回注册页
   * @param model
   * @return
   */
  @GetMapping("/page")
  public String getRegisterPage(Model model) {

    return "site/register";
  }

  /**
   * 注册用户
   *
   * @param model
   * @param user  用户信息，包括username、password、email
   * @return 用户注册失败返回错误信息回显到注册页面，注册成功跳转到操作成功页面
   */
  @PostMapping("/save")
  public String register(Model model, @Valid User user) {
    Map<String, String> map = userService.register(user);
    if (map != null && !map.isEmpty()) {                                                                      //注册失败的情况：返回错误提示
      for (Map.Entry<String, String> next : map.entrySet()) {
        model.addAttribute(next.getKey(), next.getValue());
        log.debug("错误字段：[{}], 错误原因：[{}]", next.getKey(), next.getValue());
      }
      return "site/register";
    }
    model.addAttribute("msg", "注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活");    //注册成功的情况：返回提示
    model.addAttribute("target", "/index");
    return "site/operate-result";
  }

  /**
   * 激活账号
   *
   * @param id
   * @param code
   * @return
   */
  @GetMapping("/activation/{id}/{code}")
  public String active(Model model, @PathVariable("id") Integer id, @PathVariable("code") String code) {
    int activationStatus = userService.activation(id, code);
    if (activationStatus == ACTIVATION_SUCCESS){
      model.addAttribute("msg", "成功激活账号");
      model.addAttribute("target", "/login/page");
    }else if(activationStatus == ACTIVATION_REPEAT){
      model.addAttribute("msg", "操作失败，你的账号已经激活，请勿重复激活");
      model.addAttribute("target", "/index");
    }else{
      model.addAttribute("msg", "激活失败，激活码错误");
      model.addAttribute("target", "/index");
    }
    return "site/operate-result";
  }
}
