package com.fastcampus.mobility.domain.model;

import com.fastcampus.mobility.domain.entity.DrivingEntity;
import com.fastcampus.mobility.domain.enums.CallType;
import com.fastcampus.mobility.domain.enums.DrivingStatus;
import com.fastcampus.mobility.domain.enums.VehicleType;
import com.fastcampus.mobility.dto.AbstractDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Driving extends AbstractDto {

    @EqualsAndHashCode.Include
    private Long id = 0L;
    private CallType callType;
    private VehicleType vehicleType;
    private DrivingStatus status = DrivingStatus.ALLOCATING;
    private LocalDateTime drivingStartDate;
    private LocalDateTime drivingEndDate;
    private Money fee;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private DrivingRoute drivingRoute;
    private RatePolicy ratePolicy;

    public static Driving of(DrivingEntity drivingEntity) {
        return AbstractDto.fromEntity(Driving.class, drivingEntity);
    }

//    public static Driving createDriving() {
//        Driving driving = new Driving();
//        driving.route = Route.calculateRoute();
//        AllocatePolicy.recommendVehicle(driving);
//        return driving;
//    }
//
//    public void startDriving() {
//    }
//
//    public void endDriving() {
//        this.fee = ratePolicy.calculateFee(this);
//    }
}
