package com.fastcampus.mobility.repository;

import com.fastcampus.mobility.entity.DispatchRequestEntity;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchRequestRepository extends
    PagingAndSortingRepository<DispatchRequestEntity, Long> {

  List<DispatchRequestEntity> findAllByDrivingId(final Long drivingId);
}
