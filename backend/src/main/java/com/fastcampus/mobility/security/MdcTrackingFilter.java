package com.fastcampus.mobility.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@Component
public class MdcTrackingFilter extends GenericFilterBean {

  public MdcTrackingFilter() {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    try {
      String requestURI = request.getRequestURI() +
          (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
      MDC.put("uri", requestURI);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        MDC.put("username", userDetail.getUsername());
      }
      chain.doFilter(servletRequest, servletResponse);
    } finally {
      MDC.remove("username");
      MDC.remove("uri");
    }
  }
}
