package com.fastcampus.mobility.dto.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleUpdateCommand {

  private Long vehicleId;
  @NotBlank(message = "{vehicle.NotBlank.license}")
  @Size(min = 5, message = "{vehicle.Size.min.license}")
  @Size(max = 20, message = "{vehicle.Size.max.license}")
  private String license;
  private String coordinates;
}

