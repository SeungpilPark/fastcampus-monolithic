package com.fastcampus.mobility.domain.entity;

import com.fastcampus.mobility.domain.enums.Grade;
import com.fastcampus.mobility.domain.enums.VehicleType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "license", nullable = false)
    private String license;

    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "driving_yn", nullable = false)
    private boolean drivingYn;
}

