package com.flameking.community.controller;

import com.flameking.community.entity.User;
import com.flameking.community.enums.ImageTypeEnum;
import com.flameking.community.service.UserService;
import com.flameking.community.support.CommunityConstant;
import com.flameking.community.util.CommunityUtils;
import com.flameking.community.util.FileUtils;
import com.flameking.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Validated
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {
  @Value("${server.servlet.context-path}")
  private String contextPath;

  @Value("${community.path.domain}")
  private String domain;

  @Value("${community.path.upload}")
  private String uploadPath;

  @Autowired
  private HostHolder hostHolder;

  @Autowired
  private UserService userService;

  @GetMapping("/setting")
  public String getSettingPage() {
    return "/site/setting";
  }

  @PostMapping("/upload")
  public String uploadHeader(@NotNull(message = "您还未上传任何图像") MultipartFile multipartFile, Model model) {
    String fileType = FileUtils.checkFile(multipartFile, FILE_MAXSIZE, ImageTypeEnum.getFileType());
    String fileName = CommunityUtils.generateUUID() + "." + fileType;
    File file = new File(uploadPath + fileName);
    try {
      multipartFile.transferTo(file);                                   //将文件写入磁盘
    } catch (IOException e) {
      log.debug("上传文件失败：{}", e.getMessage());
      throw new RuntimeException("上传文件失败，服务器发生异常！", e);
    }

    User user = hostHolder.getUser();                                   //更新用户头像访问路径
    String headerUrl = domain + contextPath + "/user/header/" + fileName;
    userService.updateHeaderUrlById(user.getId(), headerUrl);
    return "redirect:/index";
  }

  @GetMapping("/header/{fileName}")
  public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
    fileName = uploadPath + fileName;
    File file = new File(fileName);
    if (file.exists()) {
      try (ServletOutputStream os = response.getOutputStream();
           FileInputStream fileInputStream = new FileInputStream(file);) {

        byte[] b = new byte[1024];
        while (fileInputStream.read(b) != -1) {
          os.write(b, 0, b.length);
        }
      } catch (IOException e) {
        log.debug("读取用户头像失败：{}", e.getMessage());
      }
    }else{
      log.debug("用户头像不存在");
    }
  }

}
