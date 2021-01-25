package com.fastcampus.mobility.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomUserDetailsHelper {

  public CustomUserDetailsHelper() {
  }

  public SessionDto getLoginSession() {
    //로그인된 사용자의 경우
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
      CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
      if (customUserDetail.getSession() != null) {
        return customUserDetail.getSession();
      }
    }
    //로그인되지 않은 사용자의 경우
    return new SessionDto();
  }
}
