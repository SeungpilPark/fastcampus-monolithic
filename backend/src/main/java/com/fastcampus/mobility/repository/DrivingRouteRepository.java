package com.fastcampus.mobility.repository;

import com.fastcampus.mobility.entity.DrivingEntity;
import com.fastcampus.mobility.entity.DrivingRouteEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrivingRouteRepository extends PagingAndSortingRepository<DrivingRouteEntity, Long> {

}
