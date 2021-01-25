package com.fastcampus.mobility.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RouteResponse {

  private String startCoordinates;
  private String boardingCoordinates;
  private String destinationCoordinates;
  private String paths;
  private Integer boardingIndex;
}
