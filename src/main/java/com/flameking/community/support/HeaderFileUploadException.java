package com.flameking.community.support;

public class HeaderFileUploadException extends RuntimeException{

  private static final long serialVersionUID = -1674207549587247823L;

  public HeaderFileUploadException() {
  }

  public HeaderFileUploadException(String message) {
    super(message);
  }

  public HeaderFileUploadException(String message, Throwable cause) {
    super(message, cause);
  }

  public HeaderFileUploadException(Throwable cause) {
    super(cause);
  }

  public HeaderFileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
