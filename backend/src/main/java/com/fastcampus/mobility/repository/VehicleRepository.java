package com.fastcampus.mobility.repository;

import com.fastcampus.mobility.entity.VehicleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<VehicleEntity, Long>,
    VehicleRepositoryCustom {

}
