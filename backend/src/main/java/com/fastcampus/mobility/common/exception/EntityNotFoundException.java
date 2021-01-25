package com.fastcampus.mobility.common.exception;

public class EntityNotFoundException extends BusinessException {

  public EntityNotFoundException() {
    super("존재하지 않는 정보 입니다.");
  }

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
