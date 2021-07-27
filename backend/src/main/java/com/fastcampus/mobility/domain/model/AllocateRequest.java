package com.fastcampus.mobility.domain.model;

import com.fastcampus.mobility.domain.entity.AllocateEntity;
import com.fastcampus.mobility.dto.AbstractDto;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AllocateRequest extends AbstractDto {

    @EqualsAndHashCode.Include
    private Long id = 0L;
    private Long drivingId;
    private Long vehicleId;
    private Integer allocateAttempts;
    private String coordinates;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static AllocateRequest of(AllocateEntity entity) {
        return AbstractDto.fromEntity(AllocateRequest.class, entity);
    }

//    private Driving driving;
//    private Vehicle vehicle;
//
//    public void accept() {
//        driving.startDriving();
//    }
}
