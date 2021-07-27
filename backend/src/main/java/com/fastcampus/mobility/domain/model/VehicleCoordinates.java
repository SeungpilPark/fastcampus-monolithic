package com.fastcampus.mobility.domain.model;

import com.fastcampus.mobility.domain.entity.VehicleCoordinatesEntity;
import com.fastcampus.mobility.dto.AbstractDto;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class VehicleCoordinates extends AbstractDto {

    @EqualsAndHashCode.Include
    private Long vehicleId = 0L;
    private String coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static VehicleCoordinates of(VehicleCoordinatesEntity entity) {
        return AbstractDto.fromEntity(VehicleCoordinates.class, entity);
    }
}

