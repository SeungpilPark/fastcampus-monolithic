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
@Entity(name = "vehicle")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class VehicleEntity extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "license", nullable = false)
  private String license;

  @Column(name = "driving_yn", nullable = false)
  private boolean drivingYn;

  @Builder(toBuilder = true)
  public VehicleEntity(long id, String license, boolean drivingYn,
      LocalDateTime createDate, LocalDateTime updateDate) {
    this.id = id;
    this.license = license;
    this.drivingYn = drivingYn;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }
}

