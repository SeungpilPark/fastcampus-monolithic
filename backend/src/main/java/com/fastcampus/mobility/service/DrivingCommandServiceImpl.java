package com.fastcampus.mobility.service;

import com.fastcampus.mobility.common.exception.BusinessException;
import com.fastcampus.mobility.dto.DispatchRequestDto;
import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.DispatchAcceptanceCommand;
import com.fastcampus.mobility.dto.command.DrivingAddCommand;
import com.fastcampus.mobility.dto.search.BooleanCondition;
import com.fastcampus.mobility.dto.search.VehicleSearchDto;
import com.fastcampus.mobility.domain.enums.DrivingStatus;
import com.fastcampus.mobility.map.CoordinatesResponse;
import com.fastcampus.mobility.map.MapService;
import com.fastcampus.mobility.map.RouteResponse;
import com.fastcampus.mobility.repository.VehicleRepository;
import com.fastcampus.mobility.scheduler.CoordinatesScheduleService;
import com.fastcampus.mobility.scheduler.DispatchScheduleService;
import com.fastcampus.mobility.service.helper.DrivingErrorHandler;
import com.fastcampus.mobility.service.spec.DrivingCommandService;
import com.fastcampus.mobility.service.spec.DrivingDomainService;
import com.fastcampus.mobility.service.spec.VehicleDomainService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class DrivingCommandServiceImpl implements DrivingCommandService {

  private final DispatchScheduleService dispatchScheduleService;
  private final CoordinatesScheduleService coordinatesScheduleService;
  private final VehicleRepository vehicleRepository;
  private final DrivingDomainService drivingDomainService;
  private final VehicleDomainService vehicleDomainService;
  private final MapService mapService;
  private final DrivingErrorHandler drivingErrorHandler;

  @Autowired
  public DrivingCommandServiceImpl(
      final DispatchScheduleService dispatchScheduleService,
      final CoordinatesScheduleService coordinatesScheduleService,
      final VehicleRepository vehicleRepository,
      final DrivingDomainService drivingDomainService,
      final VehicleDomainService vehicleDomainService,
      final MapService mapService,
      final DrivingErrorHandler drivingErrorHandler
  ) {
    this.dispatchScheduleService = dispatchScheduleService;
    this.coordinatesScheduleService = coordinatesScheduleService;
    this.vehicleRepository = vehicleRepository;
    this.drivingDomainService = drivingDomainService;
    this.vehicleDomainService = vehicleDomainService;
    this.mapService = mapService;
    this.drivingErrorHandler = drivingErrorHandler;
  }

  @Override
  public DrivingDto drivingAdd(DrivingAddCommand addCommand) {
    DrivingDto drivingDto = drivingDomainService.insert(addCommand);
    try {
      dispatchScheduleService.addSchedule(drivingDto.getId(), 1);
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
    return drivingDto;
  }

  @Override
  public void dispatchRequest(Long drivingId, Integer attempts) {
    log.info("{} 번 운행 배차요청 {} 회차 시작", drivingId, attempts);
    try {
      DrivingDto drivingDto = drivingDomainService.get(drivingId);
      if (!DrivingStatus.배차중.equals(drivingDto.getStatus())) {
        log.info("{} 번 운행이 배차중 상태가 아님으로 배차종료", drivingId);
        return;
      }
      if (attempts >= 4) {
        log.info("{} 번 운행 배차3회 이상 실패로 배차실패 변경", drivingId);
        drivingDomainService.updateDispatchFail(drivingId);
        return;
      }
      // 최대 요청차량 수
      int dispatchVehicleMaxCount = attempts == 1 ? 4 : attempts == 2 ? 8 : 16;
      // 기존 요청차량수
      int previousRequestedCount = drivingDto.getDrivingRequests().size();
      List<Long> previousRequestedVehicleIdList = drivingDto.getDrivingRequests().stream()
          .map(DispatchRequestDto::getVehicleId).collect(Collectors.toList());

      List<VehicleDto> candidateVehicles = vehicleRepository
          // 운행중이 아닌 차량 구하기
          .findByBulkSearchCondition(
              VehicleSearchDto.builder().drivingYn(BooleanCondition.N).build())
          .stream()
          // 기존 요청차량이 아닌 것 필터링
          .filter(vehicle -> !previousRequestedVehicleIdList.contains(vehicle.getId()))
          // 고객위치로부터 가장 근거리인 차량중 (최대 요청차량 수 - 기존 요청차량수) 만큼 요청하기
          .sorted(Comparator.comparingDouble(vehicle -> {
            Double vlon = Double
                .valueOf(vehicle.getVehicleCoordinates().getCoordinates().split(",")[0]);
            Double vlat = Double
                .valueOf(vehicle.getVehicleCoordinates().getCoordinates().split(",")[1]);

            Double plon = Double
                .valueOf(drivingDto.getDrivingRoute().getBoardingCoordinates().split(",")[0]);
            Double plat = Double
                .valueOf(drivingDto.getDrivingRoute().getBoardingCoordinates().split(",")[1]);

            return Math.abs(plon - vlon) + Math.abs(plat - vlat);
          }))
          .limit(dispatchVehicleMaxCount - previousRequestedCount)
          .collect(Collectors.toList());

      drivingDomainService.updateDispatchRequest(drivingId, attempts, candidateVehicles);
      log.info("{} 번 운행 배차요청 {} 회차 배차요청 수 {}", drivingId, attempts, candidateVehicles.size());

      // 다음 배차 스케쥴 실행
      dispatchScheduleService.addSchedule(drivingId, attempts + 1);

    } catch (Exception ex) {
      log.error("{} 번 운행 배차요청 {} 회차 실패", drivingId, attempts, ex);
      drivingErrorHandler.systemFail(drivingId);
    } finally {
      try {
        dispatchScheduleService.removeSchedule(drivingId, attempts);
      } catch (SchedulerException e) {
        log.error("{} 번 운행 배차요청 {} 회차 스케쥴삭제 실패", drivingId, attempts, e);
      }
    }
  }

  @Override
  public void dispatchAcceptance(DispatchAcceptanceCommand acceptanceCommand) {
    Long drivingId = acceptanceCommand.getDrivingId();
    Long vehicleId = acceptanceCommand.getVehicleId();

    log.info("{} 번 운행에 {} 차량이 배차 수락", drivingId, vehicleId);
    DrivingDto drivingDto = drivingDomainService.get(drivingId);
    if (!DrivingStatus.배차중.equals(drivingDto.getStatus())) {
      throw new BusinessException("운행상태가 배차중이 아니어서 배차수락을 할 수 없습니다.");
    }
    VehicleDto vehicleDto = vehicleDomainService.get(vehicleId);
    if (vehicleDto.isDrivingYn()) {
      throw new BusinessException("차량이 이미 운행중이어서 배차수락을 할 수 없습니다.");
    }
    drivingDto.getDrivingRequests().stream()
        .filter(dispatchRequest -> dispatchRequest.getVehicleId().equals(vehicleId))
        .findAny()
        .orElseThrow(() -> new BusinessException("배차요청된 차량이 아니어서 배차수락을 할 수 없습니다."));
    try {
      // 경로 계산
      RouteResponse routeResponse = mapService.addPath(
          vehicleDto.getVehicleCoordinates().getCoordinates(),
          drivingDto.getDrivingRoute().getBoardingCoordinates(),
          drivingDto.getDrivingRoute().getDestinationCoordinates()
      );
      // 운행중 변경
      drivingDomainService.updateDriving(drivingId, vehicleId, routeResponse);
      // 차량운행중 변경
      vehicleDomainService.updateDriving(vehicleId);
      // 위치변경 스케쥴러 시작
      coordinatesScheduleService.addSchedule(drivingId);
    } catch (Exception ex) {
      log.error("{} 번 운행에 {} 차량이 배차 실패", drivingId, vehicleId, ex);
      drivingErrorHandler.systemFail(drivingId);
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void boarding(Long drivingId) {
    log.info("{} 번 운행 승객 탑승 처리", drivingId);
    drivingDomainService.updateBoarding(drivingId);
  }

  @Override
  public void drivingEnd(Long drivingId) {
    log.info("{} 번 운행 종료 처리", drivingId);
    DrivingDto drivingDto = drivingDomainService.get(drivingId);
    drivingDomainService.updateDrivingEnd(drivingId);
    vehicleDomainService.updateNotDriving(drivingDto.getVehicleId());
    try {
      coordinatesScheduleService.removeSchedule(drivingId);
    } catch (SchedulerException e) {
      log.error("{} 번 운행 종료 처리 실패", drivingId, e);
      drivingErrorHandler.systemFail(drivingId);
    }
  }

  @Override
  public void updateCoordinates(Long drivingId) {
    try {
      DrivingDto drivingDto = drivingDomainService.get(drivingId);
      if (!DrivingStatus.운행중.equals(drivingDto.getStatus())) {
        log.info("{} 번 운행이 운행중 상태가 아님으로 위치스케쥴 종료", drivingId);
        coordinatesScheduleService.removeSchedule(drivingId);
        return;
      }
      long elapsedTimeSeconds = ChronoUnit.SECONDS.between(
          drivingDto.getDrivingStartDate(), LocalDateTime.now());
      CoordinatesResponse drivingCoordinates = mapService
          .getDrivingCoordinates(drivingDto.getDrivingRoute(), elapsedTimeSeconds);

      // 차량 위치 업데이트
      String coordinates = drivingCoordinates.getCoordinates();
      vehicleDomainService.updateCoordinates(drivingDto.getVehicleId(), coordinates);

      // 승객 탑승함
      if ((drivingCoordinates.isPassengerPassed() && !drivingCoordinates.isDestinationPassed()) &&
          !drivingDto.isBoardingYn()) {
        this.boarding(drivingId);
        return;
      }
      // 운행 종료
      if (drivingCoordinates.isDestinationPassed()) {
        this.drivingEnd(drivingId);
      }
    } catch (Exception ex) {
      log.error("{} 번 운행 차량위치 업데이트 실패", drivingId, ex);
      drivingErrorHandler.systemFail(drivingId);
    }
  }

  @Override
  public DrivingDto cancel(Long drivingId) {
    log.info("{} 번 운행 정지 처리", drivingId);
    DrivingDto drivingDto = drivingDomainService.get(drivingId);
    drivingDomainService.updateDrivingCancel(drivingId);
    if (DrivingStatus.운행중.equals(drivingDto.getStatus())) {
      vehicleDomainService.updateNotDriving(drivingDto.getVehicleId());
    }
    try {
      coordinatesScheduleService.removeSchedule(drivingId);
    } catch (SchedulerException e) {
      log.error("{} 번 운행 정지 처리 실패", drivingId, e);
      drivingErrorHandler.systemFail(drivingId);
    }
    return drivingDomainService.get(drivingId);
  }
}
