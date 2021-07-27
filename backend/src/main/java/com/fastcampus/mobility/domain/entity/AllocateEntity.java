package com.fastcampus.mobility.domain.entity;

import com.fastcampus.mobility.domain.enums.AllocateStatus;
import com.fastcampus.mobility.domain.enums.CallType;
import com.fastcampus.mobility.domain.enums.DrivingStatus;
import com.fastcampus.mobility.domain.enums.VehicleType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "allocate")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AllocateEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "driving_id", nullable = false)
    private long drivingId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AllocateStatus status;

    @Column(name = "allocate_attempts", nullable = false, columnDefinition = "TINYINT")
    private int allocateAttempts;

    @Column(name = "vehicle_id")
    private long vehicleId;
}
