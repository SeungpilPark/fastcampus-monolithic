package com.fastcampus.mobility.dto.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAddCommand {

  @NotNull(message = "{user.NotNull.role}")
  private UserRole role;
  @NotBlank(message = "{user.NotBlank.loginId}")
  private String loginId;
  @NotBlank(message = "{user.NotBlank.loginPassword}")
  private String loginPassword;
  @NotBlank(message = "{user.NotBlank.name}")
  private String name;
}

