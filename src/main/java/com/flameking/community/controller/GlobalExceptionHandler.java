package com.flameking.community.controller;

import com.flameking.community.entity.User;
import com.flameking.community.pojo.LoginDTO;
import com.flameking.community.support.HeaderFileUploadException;
import com.flameking.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  @Autowired
  private HostHolder hostHolder;
  /**
   * 处理Controller方法参数校验异常
   *
   * @param model
   * @param exception 异常类
   * @return
   */
  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  public String javaBeanValidationHandler(Model model, BindException exception) {
    BindingResult bindingResult = exception.getBindingResult();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String field = fieldError.getField();
      Object rejectedValue = fieldError.getRejectedValue();
      String defaultMessage = fieldError.getDefaultMessage();

      model.addAttribute(field, defaultMessage);
      log.debug("错误字段：[{}], 错误值：[{}], 原因：[{}]", field, rejectedValue, defaultMessage);
    }

    User user = hostHolder.getUser();
    if (user != null) {                                     //拦截器抛出异常或者Controller抛出异常不会走拦截器的postHandle方法
      model.addAttribute("loginUser", user);
    }

    if (bindingResult.getTarget() instanceof LoginDTO) {
      return "/site/login";
    }

    return "/site/register";
  }

  /**
   * 处理平铺参数校验失败
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public String commonParamsValidationHandler(Model model, RuntimeException e) {
    String className = e.getMessage().substring(0, e.getMessage().lastIndexOf(": "));
    String uploadMsg = e.getMessage().substring(e.getMessage().lastIndexOf(": ") + 1);
    log.debug(uploadMsg);
    model.addAttribute("uploadMsg", uploadMsg);
    User user = hostHolder.getUser();
    if (user != null) {
      model.addAttribute("loginUser", user);
    }
    if (Objects.equals("uploadHeader.multipartFile", className)) {
      return "/site/setting";
    }
    return null;
  }

  @ExceptionHandler(HeaderFileUploadException.class)
  public String fileUploadExceptionHandler(Model model, RuntimeException e) {
    String uploadMsg = e.getMessage().substring(e.getMessage().lastIndexOf(": ") + 1);
    log.debug(uploadMsg);
    User user = hostHolder.getUser();
    if (user != null) {
      model.addAttribute("loginUser", user);
    }
    model.addAttribute("uploadMsg", uploadMsg);
    return "/site/setting";
  }
}
