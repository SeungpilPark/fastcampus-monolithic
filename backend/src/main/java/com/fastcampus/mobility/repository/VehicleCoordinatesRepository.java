package com.fastcampus.mobility.repository;

import com.fastcampus.mobility.entity.VehicleCoordinatesEntity;
import com.fastcampus.mobility.entity.VehicleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCoordinatesRepository extends PagingAndSortingRepository<VehicleCoordinatesEntity, Long> {

}
