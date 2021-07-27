package com.fastcampus.mobility.domain.entity;

import com.fastcampus.mobility.domain.enums.CallType;
import com.fastcampus.mobility.domain.enums.DrivingStatus;
import com.fastcampus.mobility.domain.enums.VehicleType;
import com.fastcampus.mobility.domain.model.Money;
import com.fastcampus.mobility.domain.model.MoneyToBigDecimalConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "driving")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DrivingEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "call_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CallType callType;

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DrivingStatus status;

    @Column(name = "driving_start_date")
    private LocalDateTime drivingStartDate;

    @Column(name = "driving_end_date")
    private LocalDateTime drivingEndDate;

    @Column(name = "fee", nullable = false)
    @Convert(converter = MoneyToBigDecimalConverter.class)
    private Money fee;
}
