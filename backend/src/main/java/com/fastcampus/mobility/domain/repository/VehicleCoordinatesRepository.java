package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.domain.entity.VehicleCoordinatesEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCoordinatesRepository extends PagingAndSortingRepository<VehicleCoordinatesEntity, Long> {

}
