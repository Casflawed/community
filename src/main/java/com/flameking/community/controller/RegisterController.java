package com.flameking.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
  @GetMapping("/page")
  public String getRegisterPage(Model model){

    return "site/register";
  }

}
