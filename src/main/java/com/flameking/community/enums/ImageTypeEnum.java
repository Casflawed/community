package com.flameking.community.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ImageTypeEnum {
  /**
   * 允许上传的附件类型集合
   */
  JPEG("jpeg", "FFD8FFE1"),
  JPG("jpg", "FFD8FFE0"),
  PNG("png", "89504E47"),
  BMP("bmp", "424D");
//  RTF("rtf", "7B5C727466"),
//  DOC("doc", "D0CF11E0"),
//  DOCX("docx", "504B030414"),
//  PDF("pdf", "255044462D312E");

  /**
   * 允许上传的文件类型的文件后缀
   */
  private final String suffixName;

  /**
   * 允许上传的文件类型的文件头信息
   */
  private final String headCode;

  /**
   * 构造方法
   *
   * @param suffixName 文件后缀名
   * @param headCode   文件头信息
   */
  ImageTypeEnum(String suffixName, String headCode) {
    this.suffixName = suffixName;
    this.headCode = headCode;
  }

  /**
   * 获取允许上传的文件类型集合
   *
   * @return List-String
   */
  public static List<String> getFileType() {
    List<String> fileTypeList = new ArrayList<>();
    for (ImageTypeEnum imageTypeEnum : ImageTypeEnum.values()) {
      fileTypeList.add(imageTypeEnum.getSuffixName());
    }
    return fileTypeList;
  }
}

