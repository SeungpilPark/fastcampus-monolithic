package com.fastcampus.mobility.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "driving")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DrivingEntity extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private DrivingStatus status;

  @Column(name = "vehicle_id", nullable = false)
  private long vehicleId;

  @Column(name = "boarding_yn", nullable = false)
  private boolean boardingYn;

  @Column(name = "dispatch_attempts", nullable = false, columnDefinition = "TINYINT")
  private int dispatchAttempts;

  @Column(name = "dispatch_vehicle_count", nullable = false, columnDefinition = "TINYINT")
  private int dispatchVehicleCount;

  @Column(name = "driving_start_date", nullable = false)
  private LocalDateTime drivingStartDate;

  @Column(name = "driving_end_date", nullable = false)
  private LocalDateTime drivingEndDate;

  @Builder
  public DrivingEntity(long id, DrivingStatus status, long vehicleId, boolean boardingYn,
      int dispatchAttempts, int dispatchVehicleCount, LocalDateTime drivingStartDate,
      LocalDateTime drivingEndDate, LocalDateTime createDate, LocalDateTime updateDate) {
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
}

