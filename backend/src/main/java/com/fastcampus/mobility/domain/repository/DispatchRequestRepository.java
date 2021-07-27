package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.domain.entity.AllocateRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispatchRequestRepository extends
    PagingAndSortingRepository<AllocateRequestEntity, Long> {

  List<AllocateRequestEntity> findAllByDrivingId(final Long drivingId);
}
