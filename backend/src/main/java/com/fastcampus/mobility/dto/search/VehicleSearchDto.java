package com.fastcampus.mobility.dto.search;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleSearchDto {

  private String license;
  private BooleanCondition drivingYn;

  @Builder
  public VehicleSearchDto(String license, BooleanCondition drivingYn) {
    this.license = license;
    this.drivingYn = drivingYn;
  }
}
