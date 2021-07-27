package com.fastcampus.mobility.dto;

import com.fastcampus.mobility.domain.entity.UserEntity;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserDto extends AbstractDto {

  @EqualsAndHashCode.Include
  private Long id = 0L;
  private UserRole role = UserRole.일반관리;
  private String loginId;
  private String loginPassword;
  private String name;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  @Builder
  public UserDto(Long id, UserRole role, String loginId, String loginPassword, String name,
      LocalDateTime createDate, LocalDateTime updateDate) {
    this.id = id;
    this.role = role;
    this.loginId = loginId;
    this.loginPassword = loginPassword;
    this.name = name;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }

  @QueryProjection
  public UserDto(UserEntity u) {
    this.id = u.getId();
    this.role = u.getRole();
    this.loginId = u.getLoginId();
    this.loginPassword = u.getLoginPassword();
    this.name = u.getName();
    this.createDate = u.getCreateDate();
    this.updateDate = u.getUpdateDate();
  }
}

