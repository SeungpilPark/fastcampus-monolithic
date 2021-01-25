package com.fastcampus.mobility.dto.command;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DrivingAddCommand {

  @NotBlank(message = "{driving.NotBlank.boardingCoordinates}")
  private String boardingCoordinates;
  @NotBlank(message = "{driving.NotBlank.destinationCoordinates}")
  private String destinationCoordinates;
}

