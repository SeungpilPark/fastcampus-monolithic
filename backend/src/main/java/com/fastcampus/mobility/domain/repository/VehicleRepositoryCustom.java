package com.fastcampus.mobility.domain.repository;

import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.search.VehicleSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleRepositoryCustom {

  Page<VehicleDto> findBySearchCondition(VehicleSearchDto vehicleSearchDto,
      Pageable pageable);

  List<VehicleDto> findByBulkSearchCondition(VehicleSearchDto vehicleSearchDto);
}
