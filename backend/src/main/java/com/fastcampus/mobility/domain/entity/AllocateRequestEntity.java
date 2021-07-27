package com.fastcampus.mobility.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "allocate_request")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AllocateRequestEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "driving_id", nullable = false)
    private long drivingId;

    @Column(name = "vehicle_id", nullable = false)
    private long vehicleId;

    @Column(name = "allocate_attempts", nullable = false, columnDefinition = "TINYINT")
    private int allocateAttempts;

    @Column(name = "coordinates", nullable = false)
    private String coordinates;
}

