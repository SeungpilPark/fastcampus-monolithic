package com.fastcampus.mobility.service.spec;


import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.DrivingAddCommand;
import com.fastcampus.mobility.dto.search.DrivingSearchDto;
import com.fastcampus.mobility.map.RouteResponse;
import com.mysql.cj.log.Log;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrivingDomainService {

  DrivingDto get(final Long drivingId);

  Page<DrivingDto> search(DrivingSearchDto drivingSearchDto, Pageable pageable);

  DrivingDto insert(final DrivingAddCommand addCommand);

  void updateSystemFail(final Long drivingId);

  void updateDispatchFail(final Long drivingId);

  void updateDispatchRequest(final Long drivingId, final Integer attempts,
      final List<VehicleDto> candidateVehicles);

  void updateDriving(final Long drivingId, final Long vehicleId, final RouteResponse routeResponse);

  void updateBoarding(final Long drivingId);

  void updateDrivingEnd(final Long drivingId);

  void updateDrivingCancel(final Long drivingId);
}
