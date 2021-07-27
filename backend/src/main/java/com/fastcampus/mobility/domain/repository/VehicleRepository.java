package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.domain.entity.VehicleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<VehicleEntity, Long>,
        VehicleRepositoryCustom {

}
