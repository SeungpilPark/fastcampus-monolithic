package com.fastcampus.mobility.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoordinatesResponse {

  private String coordinates;
  private boolean passengerPassed;
  private boolean destinationPassed;
}
