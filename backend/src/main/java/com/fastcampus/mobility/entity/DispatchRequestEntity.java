package com.fastcampus.mobility.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "dispatch_request")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DispatchRequestEntity extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "driving_id", nullable = false)
  private long drivingId;

  @Column(name = "vehicle_id", nullable = false)
  private long vehicleId;

  @Column(name = "dispatch_attempts", nullable = false, columnDefinition = "TINYINT")
  private int dispatchAttempts;

  @Column(name = "coordinates", nullable = false)
  private String coordinates;

  @Builder
  public DispatchRequestEntity(long id, long drivingId, long vehicleId, int dispatchAttempts,
      String coordinates, LocalDateTime createDate, LocalDateTime updateDate) {
    this.id = id;
    this.drivingId = drivingId;
    this.vehicleId = vehicleId;
    this.dispatchAttempts = dispatchAttempts;
    this.coordinates = coordinates;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }
}

