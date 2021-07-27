package com.fastcampus.mobility.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "driving_route")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DrivingRouteEntity extends AbstractEntity {

    @Id
    @Column(name = "driving_id", nullable = false)
    private long drivingId;

    @Column(name = "start_coordinates", nullable = false)
    private String startCoordinates;

    @Column(name = "boarding_coordinates", nullable = false)
    private String boardingCoordinates;

    @Column(name = "destination_coordinates", nullable = false)
    private String destinationCoordinates;

    @Column(name = "paths", nullable = false, columnDefinition = "LONGTEXT")
    private String paths;

    @Column(name = "boarding_index", nullable = false, columnDefinition = "INT")
    private int boardingIndex;
}

