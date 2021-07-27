package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.dto.search.DrivingSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrivingRepositoryCustom {

  Page<DrivingDto> findBySearchCondition(DrivingSearchDto drivingSearchDto,
      Pageable pageable);
}
