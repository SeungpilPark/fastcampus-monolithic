package com.fastcampus.mobility.service.spec;


import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.dto.command.DispatchAcceptanceCommand;
import com.fastcampus.mobility.dto.command.DrivingAddCommand;

public interface DrivingCommandService {

  DrivingDto drivingAdd(final DrivingAddCommand addCommand);

  void dispatchRequest(final Long drivingId, final Integer attempts);

  void dispatchAcceptance(DispatchAcceptanceCommand acceptanceCommand);

  void boarding(final Long drivingId);

  void drivingEnd(final Long drivingId);

  void updateCoordinates(final Long drivingId);

  DrivingDto cancel(final Long drivingId);
}
