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
public class DispatchRequestDto extends AbstractDto {

  @EqualsAndHashCode.Include
  private Long id = 0L;
  private Long drivingId;
  private Long vehicleId;
  private Integer dispatchAttempts = 1;
  private String coordinates;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  @Builder(toBuilder = true)
  public DispatchRequestDto(Long id, Long drivingId, Long vehicleId,
      Integer dispatchAttempts, String coordinates, LocalDateTime createDate,
      LocalDateTime updateDate) {
    this.id = id;
    this.drivingId = drivingId;
    this.vehicleId = vehicleId;
    this.dispatchAttempts = dispatchAttempts;
    this.coordinates = coordinates;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }
}

