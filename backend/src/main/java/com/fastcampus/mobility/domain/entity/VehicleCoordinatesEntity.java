package com.fastcampus.mobility.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
}

