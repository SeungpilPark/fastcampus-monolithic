package com.fastcampus.mobility.dto;

import com.fastcampus.mobility.domain.entity.VehicleCoordinatesEntity;
import com.fastcampus.mobility.domain.entity.VehicleEntity;
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
public class VehicleDto extends AbstractDto {

  @EqualsAndHashCode.Include
  private Long id = 0L;
  private String license;
  private boolean drivingYn;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
  private VehicleCoordinatesDto vehicleCoordinates;

  @Builder(toBuilder = true)
  public VehicleDto(Long id,
      String license, boolean drivingYn,
      LocalDateTime createDate, LocalDateTime updateDate) {
    this.id = id;
    this.license = license;
    this.drivingYn = drivingYn;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }

  @QueryProjection
  public VehicleDto(VehicleEntity v, VehicleCoordinatesEntity vc) {
    this.id = v.getId();
    this.license = v.getLicense();
    this.drivingYn = v.isDrivingYn();
    this.createDate = v.getCreateDate();
    this.updateDate = v.getUpdateDate();
    this.vehicleCoordinates = new VehicleCoordinatesDto(vc);
  }

  @QueryProjection
  public VehicleDto(VehicleEntity v) {
    this.id = v.getId();
    this.license = v.getLicense();
    this.drivingYn = v.isDrivingYn();
    this.createDate = v.getCreateDate();
    this.updateDate = v.getUpdateDate();
  }
}

