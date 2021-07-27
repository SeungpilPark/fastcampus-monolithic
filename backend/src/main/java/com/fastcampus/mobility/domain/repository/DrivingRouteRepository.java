package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.domain.entity.DrivingRouteEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrivingRouteRepository extends PagingAndSortingRepository<DrivingRouteEntity, Long> {

}
