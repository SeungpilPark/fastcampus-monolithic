package com.fastcampus.mobility.domain.model;

import com.fastcampus.mobility.domain.entity.VehicleEntity;
import com.fastcampus.mobility.dto.AbstractDto;
import com.fastcampus.mobility.dto.VehicleCoordinatesDto;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Vehicle extends AbstractDto {

    @EqualsAndHashCode.Include
    private Long id = 0L;
    private String license;
    private boolean drivingYn;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private VehicleCoordinatesDto vehicleCoordinates;

    public static Vehicle of(VehicleEntity entity) {
        return AbstractDto.fromEntity(Vehicle.class, entity);
    }
//    private void calculateCurrentLocation() {
//
//    }
}
