package com.fastcampus.mobility.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetail extends User {

  private SessionDto session;

  public CustomUserDetail(
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities, SessionDto sessionDto) {
    super(username, password, authorities);
    this.session = sessionDto;
  }

  public CustomUserDetail(
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public SessionDto getSession() {
    return session;
  }
}
