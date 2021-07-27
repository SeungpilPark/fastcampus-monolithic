package com.fastcampus.mobility.service.helper;

import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.domain.enums.DrivingStatus;
import com.fastcampus.mobility.scheduler.CoordinatesScheduleService;
import com.fastcampus.mobility.service.spec.DrivingDomainService;
import com.fastcampus.mobility.service.spec.VehicleDomainService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class DrivingErrorHandler {

  private final CoordinatesScheduleService coordinatesScheduleService;
  private final DrivingDomainService drivingDomainService;
  private final VehicleDomainService vehicleDomainService;

  @Autowired
  public DrivingErrorHandler(
      final CoordinatesScheduleService coordinatesScheduleService,
      final DrivingDomainService drivingDomainService,
      final VehicleDomainService vehicleDomainService
  ) {
    this.coordinatesScheduleService = coordinatesScheduleService;
    this.drivingDomainService = drivingDomainService;
    this.vehicleDomainService = vehicleDomainService;
  }

  @Async
  public void systemFail(Long drivingId) {
    log.info("{} 번 운행 시스템오류 처리", drivingId);
    DrivingDto drivingDto = drivingDomainService.get(drivingId);
    drivingDomainService.updateSystemFail(drivingId);
    if (DrivingStatus.운행중.equals(drivingDto.getStatus())) {
      vehicleDomainService.updateNotDriving(drivingDto.getVehicleId());
    }
    try {
      coordinatesScheduleService.removeSchedule(drivingId);
    } catch (SchedulerException e) {
      log.error("{} 번 운행 시스템오류 처리 실패", drivingId, e);
      throw new RuntimeException(e);
    }
  }
}
