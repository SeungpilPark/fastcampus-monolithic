package com.fastcampus.mobility.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final ObjectMapper objectMapper;
  private final CustomUserDetailsHelper userDetailsHelper;

  public CustomAuthenticationSuccessHandler(
      final ObjectMapper objectMapper,
      final CustomUserDetailsHelper userDetailsHelper) {
    this.objectMapper = objectMapper;
    this.userDetailsHelper = userDetailsHelper;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    request.getSession().setMaxInactiveInterval(60 * 60 * 3);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(
        objectMapper.writeValueAsString(userDetailsHelper.getLoginSession())
    );
  }
}
