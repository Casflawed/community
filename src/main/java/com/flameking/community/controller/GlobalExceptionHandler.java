package com.flameking.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  /**
   * 处理Controller方法参数校验异常
   *
   * @param model
   * @param exception 异常类
   * @return
   */
  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  public String exceptionHandler(Model model, BindException exception) {
    BindingResult bindingResult = exception.getBindingResult();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String field = fieldError.getField();
      Object rejectedValue = fieldError.getRejectedValue();
      String defaultMessage = fieldError.getDefaultMessage();

      model.addAttribute(field, defaultMessage);
      log.debug("错误字段：[{}], 错误值：[{}], 原因：[{}]", field, rejectedValue, defaultMessage);
    }

    return "/site/register";
  }
}
