package com.fastcampus.mobility.domain.model;

import com.fastcampus.mobility.domain.entity.DrivingRouteEntity;
import com.fastcampus.mobility.dto.AbstractDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DrivingRoute extends AbstractDto {

    @EqualsAndHashCode.Include
    private Long drivingId = 0L;
    private String startCoordinates = "";
    private String boardingCoordinates = "";
    private String destinationCoordinates = "";
    private String paths = "";
    private Integer boardingIndex = 0;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static DrivingRoute of(DrivingRouteEntity entity) {
        return AbstractDto.fromEntity(DrivingRoute.class, entity);
    }

//    public static DrivingRoute calculateRoute() {
//        return new DrivingRoute();
//    }
}
