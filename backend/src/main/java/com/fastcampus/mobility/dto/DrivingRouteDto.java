package com.fastcampus.mobility.dto;

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
public class DrivingRouteDto extends AbstractDto {

  @EqualsAndHashCode.Include
  private Long drivingId = 0L;
  private String startCoordinates = "";
  private String boardingCoordinates = "";
  private String destinationCoordinates = "";
  private String paths = "";
  private Integer boardingIndex = 0;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  @Builder(toBuilder = true)
  public DrivingRouteDto(Long drivingId, String startCoordinates,
      String boardingCoordinates, String destinationCoordinates, String paths,
      Integer boardingIndex,
      LocalDateTime createDate, LocalDateTime updateDate) {
    this.drivingId = drivingId;
    this.startCoordinates = startCoordinates;
    this.boardingCoordinates = boardingCoordinates;
    this.destinationCoordinates = destinationCoordinates;
    this.paths = paths;
    this.boardingIndex = boardingIndex;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }
}

