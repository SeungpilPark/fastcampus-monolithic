package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.domain.entity.DrivingEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrivingRepository extends PagingAndSortingRepository<DrivingEntity, Long>,
        DrivingRepositoryCustom {

}
