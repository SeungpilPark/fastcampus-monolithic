package com.fastcampus.mobility.service.spec;


import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.VehicleAddCommand;
import com.fastcampus.mobility.dto.command.VehicleUpdateCommand;

public interface VehicleCommandService {

  VehicleDto vehicleAdd(final VehicleAddCommand addCommand);

  VehicleDto vehicleUpdate(final VehicleUpdateCommand updateCommand);
}
