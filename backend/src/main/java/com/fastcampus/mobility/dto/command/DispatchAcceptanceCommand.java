package com.fastcampus.mobility.dto.command;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DispatchAcceptanceCommand {

  @NotNull(message = "{dispatch.NotNull.drivingId}")
  private Long drivingId;

  @NotNull(message = "{dispatch.NotNull.vehicleId}")
  private Long vehicleId;
}

