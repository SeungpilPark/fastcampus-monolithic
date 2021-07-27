package com.fastcampus.mobility.dto;

import com.fastcampus.mobility.domain.entity.VehicleCoordinatesEntity;
import com.querydsl.core.annotations.QueryProjection;
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
public class VehicleCoordinatesDto extends AbstractDto {

  @EqualsAndHashCode.Include
  private Long vehicleId = 0L;
  private String coordinates;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  @Builder(toBuilder = true)
  public VehicleCoordinatesDto(Long vehicleId, String coordinates, LocalDateTime createDate,
      LocalDateTime updateDate) {
    this.vehicleId = vehicleId;
    this.coordinates = coordinates;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }

  @QueryProjection
  public VehicleCoordinatesDto(VehicleCoordinatesEntity vc) {
    this.vehicleId = vc.getVehicleId();
    this.coordinates = vc.getCoordinates();
    this.createDate = vc.getCreateDate();
    this.updateDate = vc.getUpdateDate();
  }
}

