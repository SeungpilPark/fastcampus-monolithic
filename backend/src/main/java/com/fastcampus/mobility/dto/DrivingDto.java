package com.fastcampus.mobility.dto;

import com.fastcampus.mobility.entity.DrivingEntity;
import com.fastcampus.mobility.entity.DrivingStatus;
import com.fastcampus.mobility.entity.VehicleEntity;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DrivingDto extends AbstractDto {

  @EqualsAndHashCode.Include
  private Long id = 0L;
  private DrivingStatus status = DrivingStatus.배차중;
  private Long vehicleId = 0L;
  private boolean boardingYn;
  private Integer dispatchAttempts = 0;
  private int dispatchVehicleCount = 0;
  private LocalDateTime drivingStartDate;
  private LocalDateTime drivingEndDate;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  private List<DispatchRequestDto> drivingRequests;
  private DrivingRouteDto drivingRoute;
  private VehicleDto vehicle;

  @Builder(toBuilder = true)
  public DrivingDto(Long id, DrivingStatus status, Long vehicleId, boolean boardingYn,
      Integer dispatchAttempts, int dispatchVehicleCount,
      LocalDateTime drivingStartDate, LocalDateTime drivingEndDate, LocalDateTime createDate,
      LocalDateTime updateDate) {
    this.id = id;
    this.status = status;
    this.vehicleId = vehicleId;
    this.boardingYn = boardingYn;
    this.dispatchAttempts = dispatchAttempts;
    this.dispatchVehicleCount = dispatchVehicleCount;
    this.drivingStartDate = drivingStartDate;
    this.drivingEndDate = drivingEndDate;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }

  @QueryProjection
  public DrivingDto(DrivingEntity d, VehicleEntity v) {
    this.id = d.getId();
    this.status = d.getStatus();
    this.vehicleId = d.getVehicleId();
    this.boardingYn = d.isBoardingYn();
    this.dispatchAttempts = d.getDispatchAttempts();
    this.dispatchVehicleCount = d.getDispatchVehicleCount();
    this.drivingStartDate = d.getDrivingStartDate();
    this.drivingEndDate = d.getDrivingEndDate();
    this.createDate = d.getCreateDate();
    this.updateDate = d.getUpdateDate();
    if (v != null) {
      this.vehicle = new VehicleDto(v);
    }
  }
}

