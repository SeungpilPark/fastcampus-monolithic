package com.fastcampus.mobility.dto.command;

import com.fastcampus.mobility.entity.UserRole;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateCommand {

  private Long userId;
  @NotNull(message = "{user.NotNull.role}")
  private UserRole role;
  private String loginPassword;
  @NotBlank(message = "{user.NotBlank.name}")
  private String name;
}

