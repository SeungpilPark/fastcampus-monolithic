package com.fastcampus.mobility.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "vehicle_coordinates")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class VehicleCoordinatesEntity extends AbstractEntity {

  @Id
  @Column(name = "vehicle_id", nullable = false)
  private long vehicleId;

  @Column(name = "coordinates", nullable = false)
  private String coordinates;

  @Builder(toBuilder = true)
  public VehicleCoordinatesEntity(long vehicleId, String coordinates,
      LocalDateTime createDate, LocalDateTime updateDate) {
    this.vehicleId = vehicleId;
    this.coordinates = coordinates;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }
}

