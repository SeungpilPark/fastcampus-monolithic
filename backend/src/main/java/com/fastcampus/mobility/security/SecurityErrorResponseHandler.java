package com.fastcampus.mobility.security;

import com.fastcampus.mobility.controller.ApiExceptionHandler.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityErrorResponseHandler {

  private final ObjectMapper objectMapper;

  public SecurityErrorResponseHandler(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void send(HttpServletResponse response, String message)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getWriter().write(
        objectMapper.writeValueAsString(
            new ApiErrorResponse(message)
        )
    );
  }
}
