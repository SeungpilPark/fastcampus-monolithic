package com.fastcampus.mobility.service;

import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.VehicleAddCommand;
import com.fastcampus.mobility.dto.command.VehicleUpdateCommand;
import com.fastcampus.mobility.service.spec.VehicleCommandService;
import com.fastcampus.mobility.service.spec.VehicleDomainService;
import java.util.Arrays;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@Transactional
public class VehicleCommandServiceImpl implements VehicleCommandService {

  private final VehicleDomainService vehicleDomainService;

  @Autowired
  public VehicleCommandServiceImpl(
      final VehicleDomainService vehicleDomainService
  ) {
    this.vehicleDomainService = vehicleDomainService;
  }

  @Override
  public VehicleDto vehicleAdd(VehicleAddCommand addCommand) {
    String[] candidateCoordinates = new String[]{
        "127.02134,37.51097", //논현역,
        "127.04885,37.50433", //선릉역,
        "127.10014,37.51323",  //잠실역
        "127.06327,37.50862", //삼성역
        "127.05550,37.49074" //도곡역
    };
    if (StringUtils.isEmpty(addCommand.getCoordinates())) {
      addCommand.setCoordinates(
          Arrays.asList(candidateCoordinates)
              .get(new Random().nextInt(candidateCoordinates.length))
      );
    }
    return vehicleDomainService.insert(addCommand);
  }

  @Override
  public VehicleDto vehicleUpdate(VehicleUpdateCommand updateCommand) {
    VehicleDto vehicleDto = vehicleDomainService.get(updateCommand.getVehicleId());
    if (StringUtils.isEmpty(updateCommand.getCoordinates())) {
      updateCommand.setCoordinates(
          vehicleDto.getVehicleCoordinates().getCoordinates()
      );
    }
    return vehicleDomainService.update(updateCommand);
  }
}
