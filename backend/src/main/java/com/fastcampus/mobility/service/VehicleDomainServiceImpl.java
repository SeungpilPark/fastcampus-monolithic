package com.fastcampus.mobility.service;

import com.fastcampus.mobility.common.exception.EntityNotFoundException;
import com.fastcampus.mobility.dto.AbstractDto;
import com.fastcampus.mobility.dto.VehicleCoordinatesDto;
import com.fastcampus.mobility.dto.VehicleDto;
import com.fastcampus.mobility.dto.command.VehicleAddCommand;
import com.fastcampus.mobility.dto.command.VehicleUpdateCommand;
import com.fastcampus.mobility.dto.search.VehicleSearchDto;
import com.fastcampus.mobility.domain.entity.VehicleCoordinatesEntity;
import com.fastcampus.mobility.domain.entity.VehicleEntity;
import com.fastcampus.mobility.repository.VehicleCoordinatesRepository;
import com.fastcampus.mobility.repository.VehicleRepository;
import com.fastcampus.mobility.service.spec.VehicleDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class VehicleDomainServiceImpl implements VehicleDomainService {

  private final VehicleRepository vehicleRepository;
  private final VehicleCoordinatesRepository vehicleCoordinatesRepository;

  @Autowired
  public VehicleDomainServiceImpl(
      final VehicleRepository vehicleRepository,
      final VehicleCoordinatesRepository vehicleCoordinatesRepository
  ) {
    this.vehicleRepository = vehicleRepository;
    this.vehicleCoordinatesRepository = vehicleCoordinatesRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public VehicleDto get(Long vehicleId) {
    VehicleDto vehicleDto = AbstractDto.fromEntity(VehicleDto.class,
        vehicleRepository.findById(vehicleId).orElseThrow(EntityNotFoundException::new));

    vehicleDto.setVehicleCoordinates(
        AbstractDto.fromEntity(VehicleCoordinatesDto.class,
            vehicleCoordinatesRepository.findById(vehicleId)
                .orElseThrow(EntityNotFoundException::new))
    );
    return vehicleDto;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<VehicleDto> search(VehicleSearchDto vehicleSearchDto, Pageable pageable) {
    return vehicleRepository.findBySearchCondition(vehicleSearchDto, pageable);
  }

  @Override
  public VehicleDto insert(VehicleAddCommand addCommand) {
    VehicleEntity vehicleEntity = vehicleRepository.save(
        VehicleEntity.builder()
            .license(addCommand.getLicense())
            .drivingYn(false)
            .build()
    );
    vehicleCoordinatesRepository.save(
        VehicleCoordinatesEntity.builder()
            .vehicleId(vehicleEntity.getId())
            .coordinates(addCommand.getCoordinates())
            .build()
    );
    return this.get(vehicleEntity.getId());
  }

  @Override
  public VehicleDto update(VehicleUpdateCommand updateCommand) {
    VehicleEntity vehicleEntity = vehicleRepository.findById(updateCommand.getVehicleId())
        .orElseThrow(EntityNotFoundException::new);
    vehicleRepository.save(
        vehicleEntity.toBuilder()
            .license(updateCommand.getLicense())
            .build());
    VehicleCoordinatesEntity vehicleCoordinatesEntity = vehicleCoordinatesRepository
        .findById(updateCommand.getVehicleId())
        .orElseThrow(EntityNotFoundException::new);
    vehicleCoordinatesRepository.save(
        vehicleCoordinatesEntity.toBuilder()
            .coordinates(updateCommand.getCoordinates())
            .build()
    );
    return this.get(updateCommand.getVehicleId());
  }

  @Override
  public void updateDriving(Long vehicleId) {
    VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
        .orElseThrow(EntityNotFoundException::new);
    vehicleEntity.setDrivingYn(true);
    vehicleRepository.save(vehicleEntity);
  }

  @Override
  public void updateNotDriving(Long vehicleId) {
    VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
        .orElseThrow(EntityNotFoundException::new);
    vehicleEntity.setDrivingYn(false);
    vehicleRepository.save(vehicleEntity);
  }

  @Override
  public void updateCoordinates(Long vehicleId, String coordinates) {
    VehicleCoordinatesEntity vehicleCoordinatesEntity = vehicleCoordinatesRepository
        .findById(vehicleId).orElseThrow(EntityNotFoundException::new);
    vehicleCoordinatesEntity.setCoordinates(coordinates);
    vehicleCoordinatesRepository.save(vehicleCoordinatesEntity);
  }
}
