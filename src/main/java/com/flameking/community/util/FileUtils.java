package com.flameking.community.util;

import com.flameking.community.enums.ImageTypeEnum;
import com.flameking.community.support.HeaderFileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class FileUtils {
  /**
   * 文件类型和文件大小校验
   *
   * @param file            上传的附件
   * @param fileMaxSize     限制上传附件的大小
   * @param allowedFileType 限制上传附件的类型
   */
  public static String checkFile(MultipartFile file, Double fileMaxSize, List<String> allowedFileType) {
    String fileType;
    // 文件类型判断 - 校验文件后缀
    String fileName = file.getOriginalFilename();
    if (StringUtils.isNotBlank(fileName)) {
      String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
      if (!fileTypeAllowed(suffix, allowedFileType)) {
        throw new HeaderFileUploadException("文件上传类型不允许");
      }
    } else {
      throw new HeaderFileUploadException("文件上传类型不允许");
    }
    // 文件类型判断 - 校验文件头内容
    try (InputStream inputStream = file.getInputStream()) {
      // 获取到上传文件的文件头信息
      String fileHeader = FileUtils.getFileHeader(inputStream);
      if (StringUtils.isBlank(fileHeader)) {
        log.error("Failed to get file header content.");
        throw new HeaderFileUploadException("文件上传类型不允许");
      }
      // 根据上传文件的文件头获取文件的真实类型
      fileType = getFileType(fileHeader);
      if (StringUtils.isBlank(fileType) || !fileTypeAllowed(fileType, allowedFileType)) {
        log.error("Unsupported file type: [{}]", fileType);
        throw new HeaderFileUploadException("文件上传类型不允许");
      }
    } catch (IOException e) {
      log.error("Get file input stream failed.", e);
      throw new HeaderFileUploadException("附件上传失败");
    }
    // 文件大小校验 - 单位：MB
    long fileBytes = file.getSize();
    double fileSize = (double) fileBytes / 1048576;
    if (fileSize <= 0) {
      throw new HeaderFileUploadException("无效的文件");
    } else if (fileSize > fileMaxSize) {
      throw new HeaderFileUploadException("文件大小超出限制");
    }
    return fileType;
  }

  /**
   * 文件类型校验
   *
   * @param fileType    待校验的类型
   * @param allowedType 允许上传的文件类型
   * @return true - 满足，false - 不满足
   */
  private static boolean fileTypeAllowed(String fileType, List<String> allowedType) {
    if (StringUtils.isBlank(fileType) || CollectionUtils.isEmpty(allowedType)) {
      return false;
    }
    return allowedType.contains(fileType);
  }

  /**
   * 据文件的头信息获取文件类型
   *
   * @param fileHeader 文件头信息
   * @return 文件类型
   */
  public static String getFileType(String fileHeader) {
    if (fileHeader == null || fileHeader.length() == 0) {
      return null;
    }
    fileHeader = fileHeader.toUpperCase();
    ImageTypeEnum[] fileTypes = ImageTypeEnum.values();
    for (ImageTypeEnum type : fileTypes) {
      boolean b = fileHeader.startsWith(type.getHeadCode());
      if (b) {
        return type.getSuffixName();
      }
    }
    return null;
  }

  /**
   * 文件头字节数组转为十六进制编码
   *
   * @param content 文件头字节数据
   * @return 十六进制编码
   */
  private static String bytesToHexString(byte[] content) {
    StringBuilder builder = new StringBuilder();
    if (content == null || content.length <= 0) {
      return null;
    }
    String temp;
    for (byte b : content) {
      temp = Integer.toHexString(b & 0xFF).toUpperCase();
      if (temp.length() < 2) {
        builder.append(0);
      }
      builder.append(temp);
    }
    return builder.toString();
  }

  /**
   * 获取文件的文件头信息
   *
   * @param inputStream 输入流
   * @return 文件头信息
   * @throws IOException 异常
   */
  private static String getFileHeader(InputStream inputStream) throws IOException {
    byte[] content = new byte[28];
    inputStream.read(content, 0, content.length);
    return bytesToHexString(content);
  }
}

