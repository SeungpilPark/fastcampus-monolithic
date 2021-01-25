package com.fastcampus.mobility.dto.search;

import com.fastcampus.mobility.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchDto {

  private String loginId;
  private String name;
  private UserRole role;

  @Builder
  public UserSearchDto(String loginId, String name, UserRole role) {
    this.loginId = loginId;
    this.name = name;
    this.role = role;
  }
}
