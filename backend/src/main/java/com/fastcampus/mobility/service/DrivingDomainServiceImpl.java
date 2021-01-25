package com.fastcampus.mobility.service;

import com.fastcampus.mobility.common.exception.EntityNotFoundException;
import com.fastcampus.mobility.dto.AbstractDto;
import com.fastcampus.mobility.dto.DispatchRequestDto;
import com.fastcampus.mobility.dto.DrivingDto;
import com.fastcampus.mobility.dto.DrivingRouteDto;
import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.DrivingAddCommand;
import com.fastcampus.mobility.dto.search.DrivingSearchDto;
import com.fastcampus.mobility.entity.DispatchRequestEntity;
import com.fastcampus.mobility.entity.DrivingEntity;
import com.fastcampus.mobility.entity.DrivingRouteEntity;
import com.fastcampus.mobility.entity.DrivingStatus;
import com.fastcampus.mobility.map.RouteResponse;
import com.fastcampus.mobility.repository.DispatchRequestRepository;
import com.fastcampus.mobility.repository.DrivingRepository;
import com.fastcampus.mobility.repository.DrivingRouteRepository;
import com.fastcampus.mobility.service.spec.DrivingDomainService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class DrivingDomainServiceImpl implements DrivingDomainService {

  private final DrivingRepository drivingRepository;
  private final DrivingRouteRepository drivingRouteRepository;
  private final DispatchRequestRepository dispatchRequestRepository;

  @Autowired
  public DrivingDomainServiceImpl(
      final DrivingRepository drivingRepository,
      final DrivingRouteRepository drivingRouteRepository,
      final DispatchRequestRepository dispatchRequestRepository
  ) {
    this.drivingRepository = drivingRepository;
    this.drivingRouteRepository = drivingRouteRepository;
    this.dispatchRequestRepository = dispatchRequestRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public DrivingDto get(Long drivingId) {
    DrivingDto drivingDto = AbstractDto
        .fromEntity(DrivingDto.class, drivingRepository.findById(drivingId)
            .orElseThrow(EntityNotFoundException::new));

    drivingDto.setDrivingRoute(
        AbstractDto.fromEntity(DrivingRouteDto.class, drivingRouteRepository.findById(drivingId)
            .orElseThrow(EntityNotFoundException::new))
    );

    drivingDto.setDrivingRequests(
        AbstractDto.fromListEntities(DispatchRequestDto.class,
            dispatchRequestRepository.findAllByDrivingId(drivingId))
    );
    return drivingDto;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<DrivingDto> search(DrivingSearchDto drivingSearchDto, Pageable pageable) {
    return drivingRepository.findBySearchCondition(drivingSearchDto, pageable);
  }

  @Override
  public DrivingDto insert(final DrivingAddCommand addCommand) {
    DrivingEntity drivingEntity = drivingRepository.save(
        DrivingEntity.builder()
            .status(DrivingStatus.배차중)
            .vehicleId(0L)
            .boardingYn(false)
            .dispatchAttempts(0)
            .dispatchVehicleCount(0)
            .build()
    );
    drivingRouteRepository.save(
        DrivingRouteEntity.builder()
            .drivingId(drivingEntity.getId())
            .startCoordinates("")
            .boardingCoordinates(addCommand.getBoardingCoordinates())
            .destinationCoordinates(addCommand.getDestinationCoordinates())
            .paths("")
            .boardingIndex(0)
            .build()
    );
    return this.get(drivingEntity.getId());
  }

  @Override
  public void updateSystemFail(final Long drivingId) {
    DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingEntity.setStatus(DrivingStatus.운행실패);
    drivingRepository.save(drivingEntity);
  }

  @Override
  public void updateDispatchFail(final Long drivingId) {
    DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingEntity.setStatus(DrivingStatus.배차실패);
    drivingRepository.save(drivingEntity);
  }

  @Override
  public void updateDispatchRequest(final Long drivingId, final Integer attempts,
      final List<VehicleDto> candidateVehicles) {
    DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingEntity.setDispatchAttempts(attempts);
    drivingEntity.setDispatchVehicleCount(
        drivingEntity.getDispatchVehicleCount() + candidateVehicles.size()
    );
    drivingRepository.save(drivingEntity);

    candidateVehicles.forEach(vehicle -> dispatchRequestRepository.save(
        DispatchRequestEntity.builder()
            .drivingId(drivingId)
            .vehicleId(vehicle.getId())
            .dispatchAttempts(attempts)
            .coordinates(vehicle.getVehicleCoordinates().getCoordinates())
            .build()
    ));
  }

  @Override
  public void updateDriving(final Long drivingId, final Long vehicleId,
      final RouteResponse routeResponse) {
    DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingEntity.setStatus(DrivingStatus.운행중);
    drivingEntity.setBoardingYn(false);
    drivingEntity.setVehicleId(vehicleId);
    drivingEntity.setDrivingStartDate(LocalDateTime.now());
    drivingRepository.save(drivingEntity);

    DrivingRouteEntity drivingRouteEntity = drivingRouteRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingRouteEntity.setStartCoordinates(routeResponse.getStartCoordinates());
    drivingRouteEntity.setBoardingCoordinates(routeResponse.getBoardingCoordinates());
    drivingRouteEntity.setDestinationCoordinates(routeResponse.getDestinationCoordinates());
    drivingRouteEntity.setPaths(routeResponse.getPaths());
    drivingRouteEntity.setBoardingIndex(routeResponse.getBoardingIndex());
    drivingRouteRepository.save(drivingRouteEntity);
  }

  @Override
  public void updateBoarding(Long drivingId) {
    DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingEntity.setStatus(DrivingStatus.운행중);
    drivingEntity.setBoardingYn(true);
    drivingRepository.save(drivingEntity);
  }

  @Override
  public void updateDrivingEnd(Long drivingId) {
    DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingEntity.setStatus(DrivingStatus.운행종료);
    drivingEntity.setDrivingEndDate(LocalDateTime.now());
    drivingRepository.save(drivingEntity);
  }

  @Override
  public void updateDrivingCancel(Long drivingId) {
    DrivingEntity drivingEntity = drivingRepository.findById(drivingId)
        .orElseThrow(EntityNotFoundException::new);
    drivingEntity.setStatus(DrivingStatus.운행중지);
    drivingRepository.save(drivingEntity);
  }
}
