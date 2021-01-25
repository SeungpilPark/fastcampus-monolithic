package com.fastcampus.mobility.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler {

  private final ObjectMapper objectMapper;
  private final CustomUserDetailsHelper userDetailsHelper;

  public CustomLogoutSuccessHandler(
      final ObjectMapper objectMapper,
      final CustomUserDetailsHelper userDetailsHelper) {
    this.objectMapper = objectMapper;
    this.userDetailsHelper = userDetailsHelper;
  }

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(
        objectMapper.writeValueAsString(userDetailsHelper.getLoginSession())
    );
  }
}
