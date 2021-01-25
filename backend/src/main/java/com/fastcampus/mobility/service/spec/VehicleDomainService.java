package com.fastcampus.mobility.service.spec;


import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.VehicleAddCommand;
import com.fastcampus.mobility.dto.command.VehicleUpdateCommand;
import com.fastcampus.mobility.dto.search.VehicleSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleDomainService {

  VehicleDto get(final Long vehicleId);

  Page<VehicleDto> search(VehicleSearchDto vehicleSearchDto, Pageable pageable);

  VehicleDto insert(final VehicleAddCommand addCommand);

  VehicleDto update(final VehicleUpdateCommand updateCommand);

  void updateDriving(final Long vehicleId);

  void updateNotDriving(final Long vehicleId);

  void updateCoordinates(final Long vehicleId, final String coordinates);
}
