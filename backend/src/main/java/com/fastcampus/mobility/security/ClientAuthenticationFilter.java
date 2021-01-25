package com.fastcampus.mobility.security;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@Component
public class ClientAuthenticationFilter extends GenericFilterBean {

  private final SecurityErrorResponseHandler errorResponseHandler;
  private static String API_KEY_HEADER = "x-api-key";
  private static String API_KEY_VALUE = "VdDrxiT03k5pb4yNKNAkFxQ7v8wvw5DT";

  public ClientAuthenticationFilter(
      final SecurityErrorResponseHandler errorResponseHandler
  ) {
    this.errorResponseHandler = errorResponseHandler;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    // API 가 아닐경우 다음 필터로 넘어간다.
    if (!SecurityConfig.isApiPath(request)) {
      chain.doFilter(servletRequest, servletResponse);
      return;
    }

    // 클라이언트 헤더키가 없으면 다음 필터로 넘어간다.
    String apiKey = request.getHeader(API_KEY_HEADER);
    if (StringUtils.isEmpty(apiKey)) {
      chain.doFilter(servletRequest, servletResponse);
      return;
    }

    // 클라이언트 헤더키가 맞다면 인증정보를 생성하고, 필터종료시 인증정보를 삭제하여 재사용을 방지한다.
    if (API_KEY_VALUE.equals(apiKey)) {
      try {
        List<GrantedAuthority> authorities = AuthorityUtils
            .createAuthorityList("CLIENT");
        UserDetails userDetails = new CustomUserDetail(
            "system",
            "",
            authorities
        );
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities()));
        chain.doFilter(servletRequest, servletResponse);
      } finally {
        HttpSession session = request.getSession(false);
        if (session != null) {
          logger.debug("Invalidating session: " + session.getId());
          session.invalidate();
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
      }
    } else {
      errorResponseHandler.send(response, "등록되지 않은 API KEY 입니다.");
    }
  }
}
