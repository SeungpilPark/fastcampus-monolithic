package com.fastcampus.mobility.repository;

import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.search.VehicleSearchDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleRepositoryCustom {

  Page<VehicleDto> findBySearchCondition(VehicleSearchDto vehicleSearchDto,
      Pageable pageable);

  List<VehicleDto> findByBulkSearchCondition(VehicleSearchDto vehicleSearchDto);
}
