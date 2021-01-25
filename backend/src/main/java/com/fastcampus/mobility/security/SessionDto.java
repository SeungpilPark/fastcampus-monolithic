package com.fastcampus.mobility.security;

import com.fastcampus.mobility.entity.UserEntity;
import com.fastcampus.mobility.entity.UserRole;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessionDto implements Serializable {

  private Long id;
  private UserRole role;
  private String loginId;
  private String name;
  private boolean authenticated;

  @Builder
  public SessionDto(UserEntity userEntity) {
    this.id = userEntity.getId();
    this.role = userEntity.getRole();
    this.loginId = userEntity.getLoginId();
    this.name = userEntity.getName();
    this.authenticated = true;
  }
}
