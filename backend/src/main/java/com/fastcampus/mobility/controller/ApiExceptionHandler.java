package com.fastcampus.mobility.controller;

import com.fastcampus.mobility.common.exception.BusinessException;
import com.fastcampus.mobility.common.exception.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @Autowired
  public ApiExceptionHandler() {
  }

  /**
   * Business && Data Exception
   */
  @ExceptionHandler({
      BusinessException.class,
      EntityNotFoundException.class
  })
  private ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException e) {
    log.error("ApiException > BusinessException : {}", e.getMessage(), e);
    return new ResponseEntity<>(
        new ApiErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  /**
   * hibernate validator
   */
  @ExceptionHandler({
      MethodArgumentNotValidException.class,
      BindException.class
  })
  public ResponseEntity<ApiErrorResponse> handleValidation(Exception e) {
    log.error("ApiException > BusinessException : {}", e.getMessage(), e);
    String message;
    if (e instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
      message = ex.getBindingResult().getAllErrors().isEmpty() ?
          ex.getMessage() : ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    } else {
      BindException ex = (BindException) e;
      message = ex.getBindingResult().getAllErrors().isEmpty() ?
          ex.getMessage() : ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
    return new ResponseEntity<>(
        new ApiErrorResponse(message), HttpStatus.BAD_REQUEST);
  }

  /**
   * security exception
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiErrorResponse> handleException(AccessDeniedException e) {
    log.warn("ApiException > AccessDeniedException : {}", e.getMessage(), e);
    return new ResponseEntity<>(
        new ApiErrorResponse("접근이 허용되지 않은 사용자입니다."), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Broken Pipe
   */
  @ExceptionHandler({ClientAbortException.class})
  public void handleBrokenPipe(ClientAbortException e) {
    log.warn("ApiException > ClientAbortException : {}", e.getMessage(), e);
  }

  /**
   * UNKNOWN_ERROR
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
    log.error("ApiException > Exception : {}", e.getMessage(), e);
    return new ResponseEntity<>(
        new ApiErrorResponse("시스템 오류입니다."), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Getter
  @Setter
  @ToString
  public static class ApiErrorResponse {

    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(String message) {
      this.message = message;
      this.timestamp = LocalDateTime.now();
    }
  }
}
