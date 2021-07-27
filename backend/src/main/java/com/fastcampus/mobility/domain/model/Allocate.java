package com.fastcampus.mobility.domain.model;

import com.fastcampus.mobility.domain.entity.AllocateEntity;
import com.fastcampus.mobility.domain.enums.AllocateStatus;
import com.fastcampus.mobility.dto.AbstractDto;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Allocate extends AbstractDto {

    @EqualsAndHashCode.Include
    private Long id = 0L;
    private Long drivingId;
    private AllocateStatus status = AllocateStatus.PENDING_ACCEPTANCE;
    private Integer allocateAttempts;
    private Long vehicleId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static Allocate of(AllocateEntity entity) {
        return AbstractDto.fromEntity(Allocate.class, entity);
    }

//    private Driving driving;
//    private Vehicle vehicle;
//
//    public void accept() {
//        driving.startDriving();
//    }
}
